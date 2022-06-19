package ru.ahoy.domain.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("location")
    var location: Location? = null,

    @SerializedName("current")
    var current: Current? = null
) {
    companion object {
        fun fromJson(json: String?): Weather = Gson().fromJson(json, Weather::class.java)
    }
}

fun Weather.toJson(): String = Gson().toJson(this)

