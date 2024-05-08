package com.example.flickrapp.data

import android.util.Log
import com.example.flickrapp.Room.PhotoDatabase
import com.example.flickrapp.enumModel.MatchType
import com.example.flickrapp.model.FlickrPhotoModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlickrRepository(private val database: PhotoDatabase) {

    private val TAG = "FlickrRepository"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.flickr.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val flickrService = retrofit.create(FlickrService::class.java)

    suspend fun fetchPhotos(apiKey: String?, searchText: String, tags: List<String>, matchType: MatchType, maxPhotos: Int, safeSearch: Boolean): List<FlickrPhotoModel> {
        // Tags parameter based on the match type
        Log.d(TAG, "fetchPhotos: apiKey=$apiKey, searchText=$searchText, tags=$tags, matchType=$matchType, maxPhotos=$maxPhotos, safeSearch=$safeSearch")

        val tagsParam = when (matchType) {
            MatchType.ALL -> tags.joinToString(",")
            MatchType.SOME -> tags.joinToString(" OR ")
        }

        // Tags parameter, username, and safe_search in the API request
        val response = flickrService.searchPhotos(
            apiKey = apiKey ?: "",
            text = searchText,
            tags = tagsParam,
            safeSearch = if (safeSearch) 1 else 0, // Convert Boolean to Int for safe_search parameter
            perPage = maxPhotos, // Number of photos per page
        )

        if (response.isSuccessful) {
            Log.d(TAG, "fetchPhotos: Successful response received: $response")
            val flickrResponse = response.body()
            flickrResponse?.photos?.photo?.let { photoList ->
                return photoList.map { photo ->
                    // Fetching tags separately
                    val tagsResponse = photo.id.let {
                        flickrService.getPhotoTags(
                            photoId = it,
                            apiKey = apiKey ?: ""
                        )
                    }
                    val tags = tagsResponse.body()?.photo?.tags?.tag?.map { it.raw }

                    // Retrieve datetaken and title from the photo object
                    val datetaken = photo.datetaken
                    val title = photo.title

                    FlickrPhotoModel(
                        id = photo.id,
                        owner = photo.owner ?: "",
                        secret = photo.secret,
                        server = photo.server,
                        farm = photo.farm,
                        title = title,
                        isPublic = photo.isPublic,
                        isFriend = photo.isFriend,
                        isFamily = photo.isFamily,
                        datetaken = datetaken,
                        datetakengranularity = photo.datetakengranularity,
                        datetakenunknown = photo.datetakenunknown,
                        url_h = photo.url_h,
                        height_h = photo.height_h,
                        width_h = photo.width_h,
                        tags = tags
                    )
                }
            }
        } else {
            Log.e(TAG, "fetchPhotos: Error response received: $response")
        }
        return emptyList()
    }

    suspend fun fetchPhotosByUsername(
        apiKey: String?,
        username: String,
        tags: List<String>,
        maxPhotos: Int,
        safeSearch: Boolean
    ): List<FlickrPhotoModel> {
        val response = flickrService.searchPhotosByUsername(
            apiKey = apiKey ?: "",
            username = username,
            perPage = maxPhotos,
        )

        if (response.isSuccessful) {
            Log.d(TAG, "fetchPhotosByUsername: Successful response received: $response")
            val flickrResponse = response.body()
            flickrResponse?.photos?.photo?.let { photoList ->
                return photoList.map { photo ->
                    // Fetching tags separately
                    val tagsResponse = photo.id.let {
                        flickrService.getPhotoTags(
                            photoId = it,
                            apiKey = apiKey ?: ""
                        )
                    }
                    val tags = tagsResponse.body()?.photo?.tags?.tag?.map { it.raw }

                    // Retrieve datetaken and title from the photo object
                    val datetaken = photo.datetaken
                    val title = photo.title

                    FlickrPhotoModel(
                        id = photo.id,
                        owner = photo.owner ?: "",
                        secret = photo.secret,
                        server = photo.server,
                        farm = photo.farm,
                        title = title,
                        isPublic = photo.isPublic,
                        isFriend = photo.isFriend,
                        isFamily = photo.isFamily,
                        datetaken = datetaken,
                        datetakengranularity = photo.datetakengranularity,
                        datetakenunknown = photo.datetakenunknown,
                        url_h = photo.url_h,
                        height_h = photo.height_h,
                        width_h = photo.width_h,
                        tags = tags
                    )
                }
            }
        } else {
            Log.e(TAG, "fetchPhotosByUsername: Error response received: $response")
        }
        return emptyList()
    }

    suspend fun savePhoto(photo: FlickrPhotoModel) {
        database.photoDao().insertPhoto(photo)
    }
}
