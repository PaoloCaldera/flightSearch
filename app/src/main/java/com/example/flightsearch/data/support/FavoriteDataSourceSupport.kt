package com.example.flightsearch.data.support

data class FavoriteSupport(
    val id: Int,
    val departureCode: String,
    val destinationCode: String
)

val favorites = listOf(
    FavoriteSupport(
        id = 1,
        departureCode = "BGY",
        destinationCode = "FCO"
    ),
    FavoriteSupport(
        id = 2,
        departureCode = "FCO",
        destinationCode = "BGY"
    ),
    FavoriteSupport(
        id = 3,
        departureCode = "BGY",
        destinationCode = "ARN"
    ),
    FavoriteSupport(
        id = 4,
        departureCode = "BGY",
        destinationCode = "MAD"
    ),
    FavoriteSupport(
        id = 5,
        departureCode = "MAD",
        destinationCode = "BGY"
    ),
    FavoriteSupport(
        id = 6,
        departureCode = "ARN",
        destinationCode = "BGY"
    ),
    FavoriteSupport(
        id = 7,
        departureCode = "AMS",
        destinationCode = "MUC"
    )
)