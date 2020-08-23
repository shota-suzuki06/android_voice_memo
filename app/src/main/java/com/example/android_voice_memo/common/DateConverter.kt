package com.example.android_voice_memo.common

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.let { it.time }
    }

}