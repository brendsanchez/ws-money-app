package com.dusk.ws_dollar.dto

import java.math.BigDecimal

data class PriceVal(
    val valueText: String,
    val value: BigDecimal
)