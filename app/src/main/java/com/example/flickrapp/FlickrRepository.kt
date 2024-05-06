package com.example.newlist.data

import android.util.Log
import com.example.flickrapp.FlickrPhotoModel
import com.example.flickrapp.FlickrService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class FlickrRepository {
    private val TAG = "FlickrRepository"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.flickr.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val flickrService = retrofit.create(FlickrService::class.java)

    suspend fun fetchPhotos(apiKey: String?, searchText: String): List<FlickrPhotoModel> {
        try {
            Log.d(
                TAG,
                "fetchPhotos: Fetching photos from Flickr API with search text: $searchText and safe_search set to 'safe'"
            )
            val response = flickrService.searchPhotos(
                apiKey = apiKey ?: "",
                text = searchText,
                safeSearch = 1 // Set safe_search parameter to 1 (safe)
            )
            Log.d(TAG, "fetchPhotos: Response received: $response")
            if (response.isSuccessful) {
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
        } catch (e: IOException) {
            Log.e(TAG, "fetchPhotos: IOException occurred", e)
        } catch (e: Exception) {
            Log.e(TAG, "fetchPhotos: Exception occurred", e)
        }
        return emptyList()
    }
}
