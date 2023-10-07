package com.dusk.ws_dollar.client
import com.dusk.ws_dollar.dto.DollarResponse
import com.dusk.ws_dollar.dto.Price

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DollarService {
    @GET("/v1/dollar")
    fun getDollars(@Query("web") web:String): Call<DollarResponse<List<Price>>>
}