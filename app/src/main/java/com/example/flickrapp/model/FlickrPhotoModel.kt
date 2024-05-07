package com.example.flickrapp.model

data class FlickrPhotoModel(
    val id: String,
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
