package ru.ahoy.domain.models

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("location")
    var location: Location? = null,

    @SerializedName("current")
    var current: Current? = null
)