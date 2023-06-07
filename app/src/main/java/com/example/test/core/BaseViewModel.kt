package com.example.test.core

import android.util.Log
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected open fun processError(throwable: Throwable) {
        Log.d(this::class.java.simpleName, throwable.stackTraceToString())
    }
}