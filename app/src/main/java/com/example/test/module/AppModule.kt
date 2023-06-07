package com.example.test.module

import android.content.Context
import com.example.test.diary.DiaryRepository
import com.example.test.diary.DiaryViewModel
import com.example.test.diary.model.database.DiaryDao
import com.example.test.helper.AppDatabase
import com.example.test.ui.MainRepository
import com.example.test.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    fun provideExerciseDao(context: Context): DiaryDao = AppDatabase.getInstance(context).getDiaryDao()

    single { provideExerciseDao(get()) }

    singleOf(::DiaryRepository)
    singleOf(::MainRepository)

    viewModelOf(::DiaryViewModel)
    viewModelOf(::MainViewModel)
}