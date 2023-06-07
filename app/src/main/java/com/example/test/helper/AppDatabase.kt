package com.example.test.helper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.test.converter.LocalDateConverter
import com.example.test.diary.model.database.ExerciseEntity
import com.example.test.diary.model.database.DiaryDao

@Database(
    version = 1,
    entities = [
        ExerciseEntity::class
    ]
)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDiaryDao(): DiaryDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "test.db").build()
    }
}