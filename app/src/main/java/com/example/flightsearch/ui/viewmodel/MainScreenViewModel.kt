package com.example.flightsearch.ui.viewmodel

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.entity.Airport
import com.example.flightsearch.data.entity.Favorite
import com.example.flightsearch.repository.FlightSearchDatabaseRepository
import com.example.flightsearch.repository.FlightSearchPreferencesRepository
import com.example.flightsearch.ui.model.FlightUiState
import com.example.flightsearch.microphone.model.MicState
import com.example.flightsearch.microphone.VoiceRecognitionService
import com.example.flightsearch.microphone.model.VoiceState
import com.example.flightsearch.ui.model.MicUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val preferencesRepository: FlightSearchPreferencesRepository,
    private val roomRepository: FlightSearchDatabaseRepository,
    private val applicationContext: Context
) : ViewModel() {

    // Normal variable used to save the reference to the service connection
    private var voiceServiceConnection: ServiceConnection? = null

    // User is in searching mode. true -> SearchFragment; false -> FlightsFragment
    private val _isSearchingUiState = MutableStateFlow(false)
    val isSearchingUiState: StateFlow<Boolean> = _isSearchingUiState.asStateFlow()

    // Text that is typed or recorded by the user in searching mode
    private val _searchTextUiState = MutableStateFlow("")
    val searchTextUiState: StateFlow<String> = _searchTextUiState.asStateFlow()

    // The current airport selected by the user, that pilots the FlightsFragment
    val userSelection: StateFlow<String> = preferencesRepository.userSelection
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    // Permission state for audio recording: when true for the first time, it starts the Service
    private val recordAudioPermission = MutableStateFlow(false).also {
        viewModelScope.launch {
            it.collect { isGranted ->
                if (isGranted) {
                    setIsSearching(true)
                    startListening()
                }
            }
        }
    }

    // State related to the speech recognition when clicking the microphone
    private val _micUiState = MutableStateFlow(MicUiState())
    val micUiState: StateFlow<MicUiState> = _micUiState.asStateFlow()

    // List of airports to display when user is in searching mode
    val searchList: StateFlow<List<Airport>> = _searchTextUiState.map {
        roomRepository.getFilteredAirports(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // List of flights, with departure and destination, to be displayed when not in searching mode
    val flightsList: StateFlow<List<FlightUiState>> =
        combine(
            preferencesRepository.userSelection,
            roomRepository.getFavorites()
        ) { selection, favorites ->
            if (selection.isNotEmpty()) {
                val list = roomRepository.getAllAirports()
                val departure = list.first { it.iataCode == selection }
                list.filter { it.iataCode != selection }.map {
                    val isFavorite = favorites.any { favorite ->
                        favorite.departureCode == departure.iataCode && favorite.destinationCode == it.iataCode
                    }
                    FlightUiState(
                        departure = departure,
                        destination = it,
                        isFavorite = isFavorite
                    )
                }
            } else emptyList()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // List of favorite flights, displayed only if the userSelection is empty
    val favoritesList: StateFlow<List<FlightUiState>> =
        roomRepository.getFavorites().map { favorites ->
            favorites.map {
                FlightUiState(
                    departure = roomRepository.getAirportByCode(it.departureCode),
                    destination = roomRepository.getAirportByCode(it.destinationCode),
                    isFavorite = true
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun setSearchText(value: String) {
        _searchTextUiState.value = value
    }

    fun setIsSearching(value: Boolean) {
        _isSearchingUiState.value = value
    }

    fun onUserSelection(iataCode: String) {
        viewModelScope.launch {
            preferencesRepository.saveUserSelectionPreference(iataCode)
        }
        setSearchText(iataCode)
        setIsSearching(false)
    }

    fun clearUserSelection() {
        viewModelScope.launch {
            preferencesRepository.saveUserSelectionPreference("")
        }
        setSearchText("")
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            roomRepository.saveFavorite(favorite)
        }
    }

    fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch {
            roomRepository.removeFavorite(
                roomRepository.getFavoritesByCodes(
                    departureCode = favorite.departureCode,
                    destinationCode = favorite.destinationCode
                ).first()
            )
        }
    }

    fun updateRecordAudioPermission() {
        recordAudioPermission.update { true }
    }

    fun startListening() {
        val intent = Intent(applicationContext, VoiceRecognitionService::class.java)
        voiceServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val voiceRecognitionService =
                    (service as VoiceRecognitionService.VoiceRecognitionServiceBinder).service
                viewModelScope.launch {
                    voiceRecognitionService.voiceState.collect { voiceState ->
                        handleVoiceState(voiceState)
                    }
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {}
        }
        applicationContext.apply {
            startService(intent)
            bindService(intent, voiceServiceConnection!!, Context.BIND_AUTO_CREATE)
        }
    }

    fun stopListening() {
        voiceServiceConnection?.let {
            val intent = Intent(applicationContext, VoiceRecognitionService::class.java)
            applicationContext.apply {
                unbindService(voiceServiceConnection!!)
                stopService(intent)
            }
        }
    }

    private fun handleVoiceState(voiceState: VoiceState) {
        when (voiceState.state) {
            MicState.OFF -> {
                _micUiState.update { it.reset() }
            }

            MicState.READY_FOR_SPEECH -> {
                _micUiState.update {
                    it.copy(dialogText = voiceState.result, showDialog = true)
                }
            }

            MicState.BEGINNING_OF_SPEECH -> {
                _micUiState.update { it.copy(dialogText = voiceState.result) }
            }

            MicState.SPEAKING -> {
                _micUiState.update { it.copy(dialogText = voiceState.result) }
            }

            MicState.END_OF_SPEECH -> {
                _micUiState.update { it.copy(showDialog = false) }
            }

            MicState.RESULT -> {
                _micUiState.update { it.copy(dialogText = voiceState.result) }
                _searchTextUiState.value = voiceState.result
                stopListening()
            }

            MicState.ERROR -> {
                _micUiState.update { it.reset() }
                stopListening()
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                MainScreenViewModel(
                    preferencesRepository = application.flightSearchPreferencesRepository,
                    roomRepository = application.flightSearchDatabaseRepository,
                    applicationContext = application.applicationContext
                )
            }
        }
    }
}