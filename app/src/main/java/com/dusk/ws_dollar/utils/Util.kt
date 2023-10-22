package com.dusk.ws_dollar.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Util {
    fun dateFormatted(): String {
        val formatUpdated = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return formatUpdated.format(Date())
    }

    fun dateFormatted(timestamp :Date): String {
        val format = SimpleDateFormat("dd MMM HH:mm:ss z yyyy", Locale.getDefault())
        val diaSem = SimpleDateFormat("E", Locale.getDefault()).format(timestamp)
        val diaSemPascal = diaSem.replaceFirstChar { it.uppercase() }
        return "$diaSemPascal ${format.format(timestamp)}"
    }
}