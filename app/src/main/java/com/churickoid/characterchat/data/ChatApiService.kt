package com.churickoid.characterchat.data


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatApiService {

    @GET("/")
    fun sendMessage(@Query("text") text: String): Call<String>

}