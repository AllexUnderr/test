package com.example.test.ui

import com.example.test.core.BaseViewModel
import com.example.test.helper.SingleLiveEvent

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {

    val openWebViewCommand = SingleLiveEvent<Boolean>()

    fun init() {
        openWebViewCommand.value = !repository.checkIsEmulator()
    }
}