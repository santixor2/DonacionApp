package com.example.donacionapp.util

import java.util.concurrent.TimeUnit
private const val SECOND_MILLIS = 1
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

object TimeAgo {
    fun getTimeAgo(time: Int): String {

        val now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        if(time > now || time <= 0){
            return "in the future"
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> "En este momento"
            diff < 2 * MINUTE_MILLIS -> "hace un minuto"
            diff < 60 * MINUTE_MILLIS -> "hace ${diff / MINUTE_MILLIS} minutos"
            diff < 2 * HOUR_MILLIS -> "hace una hora"
            diff < 24 * HOUR_MILLIS -> " hace ${diff / HOUR_MILLIS} horas"
            diff < 48 * HOUR_MILLIS -> "ayer"
            else -> "hace ${diff / DAY_MILLIS} dias"
        }
    }
}