package com.nitish.kilofarms


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("skuId")
    val skuId: String,
    @SerializedName("skuName")
    val skuName: String
)