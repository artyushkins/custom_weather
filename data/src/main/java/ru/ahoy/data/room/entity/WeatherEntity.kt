package ru.ahoy.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WeatherEntity {
    @PrimaryKey
    var name: String = ""
}