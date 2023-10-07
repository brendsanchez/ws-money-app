package com.dusk.ws_dollar.dto

import java.util.Date

data class Price(
    val name: String,
    val buy: PriceVal,
    val sell: PriceVal,
    val timestamp: Date
)