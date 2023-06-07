package com.example.test.diary.model

import java.time.LocalDate

data class Exercise(
    val name: String,
    val weight: Int,
    val repetition: Int,
    val date: LocalDate
)