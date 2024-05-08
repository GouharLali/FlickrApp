package com.example.flickrapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapp.MyApp
import com.example.flickrapp.data.FlickrRepository
import com.example.flickrapp.enumModel.MatchType
import com.example.flickrapp.model.FlickrPhotoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlickrViewModel(application: Application, private val repository: FlickrRepository) : AndroidViewModel(application) {

    private val _photos: MutableStateFlow<List<FlickrPhotoModel>> = MutableStateFlow(emptyList())
    val photos: StateFlow<List<FlickrPhotoModel>> = _photos

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    // Constructor that accepts Application and FlickrRepository
    constructor(application: Application) : this(application, FlickrRepository(database = MyApp.database)) {
        //Add additional initialization logic here if needed
    }

    fun fetchPhotos(
        apiKey: String?,
        searchText: String,
        tags: List<String>,
        matchType: MatchType,
        maxPhotos: Int,
        safeSearch: Boolean
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val fetchedPhotos = repository.fetchPhotos(apiKey, searchText, tags, matchType, maxPhotos, safeSearch)
                _photos.value = fetchedPhotos
                // Save fetched photos to the local database
                fetchedPhotos.forEach { photo ->
                    savePhotoToLocalDatabase(photo)
                }
            } catch (e: Exception) {
                // Handle exceptions
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchPhotosByUsername(
        apiKey: String?,
        username: String,
        tags: List<String>,
        maxPhotos: Int,
        safeSearch: Boolean
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val fetchedPhotos = repository.fetchPhotosByUsername(apiKey, username, tags, maxPhotos, safeSearch)
                _photos.value = fetchedPhotos
                // Save fetched photos to the local database
                fetchedPhotos.forEach { photo ->
                    savePhotoToLocalDatabase(photo)
                }
            } catch (e: Exception) {
                // Handle exceptions
            } finally {
                _loading.value = false
            }
        }
    }

    private fun savePhotoToLocalDatabase(photo: FlickrPhotoModel) {
        viewModelScope.launch {
            repository.savePhoto(photo)
        }
    }
}
