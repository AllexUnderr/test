package com.example.test.ui

import android.os.Build
import com.example.test.BuildConfig
import java.util.Locale


class MainRepository {
    fun checkIsEmulator(): Boolean =
        if (BuildConfig.DEBUG)
            false
        else
            isEmulator()

    private fun isEmulator(): Boolean {
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