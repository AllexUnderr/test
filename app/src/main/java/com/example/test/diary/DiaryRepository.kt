package com.example.test.diary

import com.example.test.diary.model.Exercise
import com.example.test.diary.model.database.ExerciseEntity
import com.example.test.diary.model.database.DiaryDao

class DiaryRepository(private val diaryDao: DiaryDao) {

    suspend fun saveExercise(exercise: Exercise): Boolean {
        if (!isFieldsValid(exercise))
            return false

        diaryDao.insertExercise(
            convertModelToEntity(exercise)
        )

        return true
    }

    suspend fun getExercises(): List<Exercise> =
        diaryDao.getExercises().convertEntityListToModel()

    private fun isFieldsValid(exercise: Exercise): Boolean {
        if (hasEmptyField(exercise)) return false

        return exercise.weight >= 0 && exercise.repetition > 0
    }

    private fun hasEmptyField(exercise: Exercise) =
        exercise.name.trim().isBlank() || exercise.repetition == 0

    private fun convertModelToEntity(exercise: Exercise): ExerciseEntity =
        ExerciseEntity(
            name = exercise.name,
            weight = exercise.weight,
            repetition = exercise.repetition,
            date = exercise.date
        )

    private fun List<ExerciseEntity>.convertEntityListToModel(): List<Exercise> =
        this.map {
            Exercise(
                name = it.name,
                weight = it.weight,
                repetition = it.repetition,
                date = it.date
            )
        }
}