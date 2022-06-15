package ru.ahoy.data.network.models.responses

import com.google.gson.annotations.SerializedName
import ru.ahoy.data.network.models.Current
import ru.ahoy.data.network.models.Location


data class ResponseCurrent(
    @SerializedName("location")
    var location: Location? = null,

    @SerializedName("current")
    var current: Current? = null
) : BaseResponse()