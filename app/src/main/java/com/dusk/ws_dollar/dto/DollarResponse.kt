package com.dusk.ws_dollar.dto

data class DollarResponse<T>(
    val code: Int,

    val message: String,

    val data: T
)