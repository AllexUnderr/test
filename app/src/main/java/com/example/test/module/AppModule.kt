package com.example.test.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.test.diary.DiaryRepository
import com.example.test.diary.DiaryViewModel
import com.example.test.diary.model.database.DiaryDao
import com.example.test.helper.AppDatabase
import com.example.test.network.Connection
import com.example.test.ui.MainRepository
import com.example.test.ui.MainViewModel
import com.example.test.browser.BrowserViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("url_storage")

val appModule = module {

    fun provideExerciseDao(context: Context): DiaryDao = AppDatabase.getInstance(context).getDiaryDao()

    single { provideExerciseDao(get()) }

    singleOf(::DiaryRepository)
    singleOf(::MainRepository)
    singleOf(::Connection)

    viewModelOf(::DiaryViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::BrowserViewModel)
}