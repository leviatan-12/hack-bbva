package com.idemia.biosmart.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeConverter {
    companion object {
        fun timeFromLong(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            return format.format(date)
        }
    }
}