package com.example.flickrapp.data

import com.example.flickrapp.model.FlickrResponseModel
import com.example.flickrapp.model.PhotoTagsResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {
    @GET("services/rest")
    suspend fun searchPhotos(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("extras") extras: String = "date_taken,url_h",
        @Query("text") text: String, // Search text query
        @Query("safe_search") safeSearch: Int = 1, // Set safe_search parameter to 1 (safe)
        @Query("tags") tags: String, // Tags to be matched
        @Query("tag_mode") tagMode: String = "any", // Match type: "any" for some, "all" for all
        @Query("per_page") perPage: Int // Number of photos per page
    ): Response<FlickrResponseModel>

    @GET("services/rest")
    suspend fun getPhotoTags(
        @Query("method") method: String = "flickr.tags.getListPhoto",
        @Query("photo_id") photoId: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): Response<PhotoTagsResponseModel>
}
