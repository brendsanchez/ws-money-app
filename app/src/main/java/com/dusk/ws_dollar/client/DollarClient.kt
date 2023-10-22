package com.dusk.ws_dollar.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DollarClient {
    private val baseUrl: String = System.getenv("API_WS_DOLLAR")
        ?: "https://ae13035b-30bb-4c3a-a128-526c01701707.mock.pstmn.io"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: DollarService = retrofit.create(DollarService::class.java);

    init { // todo remove later
        println("Valor de baseUrl: $baseUrl")
    }
}