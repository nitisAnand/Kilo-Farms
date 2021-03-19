package com.nitish.kilofarms


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    val `data`: List<DataX>,
    @SerializedName("response")
    val response: Int
)