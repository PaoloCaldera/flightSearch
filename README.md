# Flight Search

Flight Search is the test project of the sixth unit of the *Android Basics with Compose* learning path, provided by Android Developers. It focuses on storing complex data and user preferences permanently by exploiting libraries called [Room](https://developer.android.com/jetpack/androidx/releases/room) and [DataStore](https://developer.android.com/jetpack/androidx/releases/datastore). In detail, the application uses flights data stored in a SQLite database to list all the possible flights departing from a specific airport, and gives the user the possibility to save favorite flights.

The project demonstrates the ability to handle complex data storage with SQLite database and database entities with [Room](https://developer.android.com/jetpack/androidx/releases/room) by reading, creating, updating and deleting entity records. Moreover, it shows the capability of saving user preferences inside a [DataStore](https://developer.android.com/jetpack/androidx/releases/datastore) object. The main topics covered in this project are listed below:

* [Room](https://developer.android.com/jetpack/androidx/releases/room) for storing complex data structures, modify stored data and emit data changes
* [DataStore](https://developer.android.com/jetpack/androidx/releases/datastore) for saving user preferences in key-value pairs, maintained until the user clears application cache and data
* [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines), to apply multithreading to your app implementation
* [Accompanist](https://github.com/google/accompanist) for handling permissions related to the usage of hardware components, in this case the microphone
* [SpeechRecognizer](https://developer.android.com/reference/android/speech/SpeechRecognizer) and [RecognitionListener](https://developer.android.com/reference/android/speech/RecognitionListener), a speech to text component, to record the user's speech when using the microphone

The project comprises two screens with a common search bar, with the layout developed through Jetpack Compose. The theme has been selected and created with the help of [Material Design 3 Theme Builder](https://m3.material.io/theme-builder#/custom).

Visit the [Wiki](https://github.com/PaoloCaldera/flightSearch/wiki) to see the application screens.


## Getting Started

To clone the repository, use the command
```
$ git clone https://github.com/PaoloCaldera/flightSearch.git
```
or the `Get from VCS` option inside Android Studio by copying the link above.

Then, run the application on an Android device or emulator. The application is compiled with API 36, thus use a device or emulator supporting such API version.

## License

Flight Search is a public project that can be downloaded and modified under the terms and conditions of the [MIT License](LICENSE).
