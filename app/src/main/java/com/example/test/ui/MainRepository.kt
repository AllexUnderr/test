package com.example.test.ui

import android.content.Context
import android.os.Build
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.test.BuildConfig
import com.example.test.module.dataStore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class MainRepository(private val context: Context) {

    private val urlKey = stringPreferencesKey("url")

    private val url: Flow<String> = context.dataStore.data.map { it[urlKey] ?: "" }

    suspend fun getUrl(): Flow<String> =
        url.map {
            if (it.isBlank()) {
                val urlToSave = getRemoteUrl()
                saveUrl(urlToSave)

                urlToSave
            } else {
                it
            }
        }

    private suspend fun saveUrl(url: String) {
        context.dataStore.edit {
            it[urlKey] = url
        }
    }

    private fun getRemoteUrl(): String =
        Firebase.remoteConfig.getString("url")

    fun isEmulator(): Boolean {
        if (BuildConfig.DEBUG)
            return false

        val phoneModel = Build.MODEL
        val buildHardware = Build.HARDWARE
        val buildProduct = Build.PRODUCT
        val brand = Build.BRAND

        return (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware == "goldfish"
                || brand.contains("google")
                || buildHardware == "vbox86"
                || buildProduct == "sdk"
                || buildProduct == "google_sdk"
                || buildProduct == "sdk_x86"
                || buildProduct == "vbox86p"
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox"))
                || (brand.startsWith("generic") && Build.DEVICE.startsWith("generic"))
    }
}