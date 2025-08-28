package com.example.flightsearch.data

data class Favorite(
    val id: Int,
    val departureCode: String,
    val destinationCode: String
)

val favorites = listOf(
    Favorite(
        id = 1,
        departureCode = "BGY",
        destinationCode = "FCO"
    ),
    Favorite(
        id = 2,
        departureCode = "FCO",
        destinationCode = "BGY"
    ),
    Favorite(
        id = 3,
        departureCode = "BGY",
        destinationCode = "ARN"
    ),
    Favorite(
        id = 4,
        departureCode = "BGY",
        destinationCode = "MAD"
    ),
    Favorite(
        id = 5,
        departureCode = "MAD",
        destinationCode = "BGY"
    ),
    Favorite(
        id = 6,
        departureCode = "BGY",
        destinationCode = "ARN"
    ),
    Favorite(
        id = 7,
        departureCode = "AMS",
        destinationCode = "MUC"
    )
)