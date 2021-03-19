package com.nitish.kilofarms

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("vegetable?item=all&userId=1")
    fun getData(): Call<Data>


    @POST("vegetables")
    fun postData(@Body dataSend: DataSend): Call<DataSend>

}