package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.RecordingDao

class AppContainer(private val context: Context) {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "screenstudio_db"
        ).build()
    }
    
    val recordingDao: RecordingDao
        get() = database.recordingDao()
}
