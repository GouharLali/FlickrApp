package com.example.flickrapp

import android.app.Application
import androidx.room.Room
import com.example.flickrapp.Room.PhotoDatabase

class MyApp : Application() {
    companion object {
        lateinit var database: PhotoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            PhotoDatabase::class.java, "photo-database"
        ).build()
    }
}
