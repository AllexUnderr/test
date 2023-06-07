package com.example.test.diary.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.test.converter.LocalDateConverter
import java.time.LocalDate

@Entity
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val weight: Int,
    val repetition: Int,
    @TypeConverters(LocalDateConverter::class)
    val date: LocalDate
)