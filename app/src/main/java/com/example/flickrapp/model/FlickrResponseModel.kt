package com.example.flickrapp.model

data class FlickrResponseModel(
    val photos: PhotosData,
    val stat: String
)

data class PhotosData(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: List<FlickrPhotoModel>
)