package com.dusk.ws_dollar.client

import com.dusk.ws_dollar.dto.DollarResponse
import com.dusk.ws_dollar.dto.Page
import com.dusk.ws_dollar.dto.Price
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DollarService {

    @GET("/v1/dollar")
    suspend fun getDollars(@Query("web") web: String): Response<DollarResponse<List<Price>>>

    @GET("/v1/pages")
    fun getPages(): Call<DollarResponse<List<Page>>>
}