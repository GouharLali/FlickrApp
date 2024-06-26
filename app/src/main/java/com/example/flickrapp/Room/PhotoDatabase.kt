package com.example.flickrapp.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flickrapp.model.FlickrPhotoModel

@Database(entities = [FlickrPhotoModel::class], version = 1, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

}
