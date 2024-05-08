package com.example.flickrapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "photos")
@TypeConverters(Converters::class)
data class FlickrPhotoModel(
    @PrimaryKey val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val isPublic: Int,
    val isFriend: Int,
    val isFamily: Int,
    val datetaken: String,
    val datetakengranularity: Int,
    val datetakenunknown: String,
    val url_h: String?,
    val height_h: Int,
    val width_h: Int,
    val tags: List<String>?
)

