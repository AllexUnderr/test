package com.example.test.webView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test.core.BaseViewModel

class BrowserViewModel : BaseViewModel() {

    private val _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String> = _url

    fun setUrl(url: String) {
        _url.value = url
    }
}