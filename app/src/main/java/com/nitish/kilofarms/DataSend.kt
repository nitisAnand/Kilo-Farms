package com.nitish.kilofarms


import com.google.gson.annotations.SerializedName

data class DataSend(

    @SerializedName("skuName")
    val skuName: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("skuUnit")
    val skuUnit: String,
    @SerializedName("skuCategory")
    val skuCategory: String

)