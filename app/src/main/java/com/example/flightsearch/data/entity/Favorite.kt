package com.example.flightsearch.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite",
    foreignKeys = [
        ForeignKey(
            entity = Airport::class,
            parentColumns = ["id"],
            childColumns = ["departure_id"],
            onUpdate = ForeignKey.Companion.CASCADE,
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = Airport::class,
            parentColumns = ["id"],
            childColumns = ["destination_id"],
            onUpdate = ForeignKey.Companion.CASCADE,
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "departure_id") val departureId: Int,
    @ColumnInfo(name = "destination_id") val destinationId: Int
)