package com.example.test.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.test.core.BaseViewModel
import com.example.test.diary.model.Exercise
import com.example.test.helper.SingleLiveEvent
import kotlinx.coroutines.launch

class DiaryViewModel(private val repository: DiaryRepository) : BaseViewModel() {

    private val _exercises: MutableLiveData<List<Exercise>> = MutableLiveData()
    val exercises: LiveData<List<Exercise>> = _exercises

    val hasEmptyField: SingleLiveEvent<Boolean> = SingleLiveEvent()

    init {
        viewModelScope.launch {
            loadExerciseList()
        }
    }

    fun onSubmitButton(exercise: Exercise) {
        viewModelScope.launch {
            saveExercise(exercise)

            loadExerciseList()
        }
    }

    private suspend fun saveExercise(exercise: Exercise) {
        try {
            hasEmptyField.value = !repository.saveExercise(exercise)
        } catch (e: Exception) {
            processError(e)
        }
    }

    private suspend fun loadExerciseList() {
        try {
            _exercises.value = repository.getExercises()
        } catch (e: Exception) {
            processError(e)
        }
    }
}