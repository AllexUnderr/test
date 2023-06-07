package com.example.test.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateConverter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @TypeConverter
    fun convert(string: String): LocalDate =
        LocalDate.parse(string, formatter)

    @TypeConverter
    fun convert(date: LocalDate): String =
        formatter.format(date)
}