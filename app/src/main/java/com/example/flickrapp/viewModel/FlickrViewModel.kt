package com.example.flickrapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapp.enumModel.MatchType
import com.example.flickrapp.data.FlickrRepository
import com.example.flickrapp.model.FlickrPhotoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlickrViewModel : ViewModel() {
    private val repository = FlickrRepository()

    private val _photos = MutableStateFlow<List<FlickrPhotoModel>>(emptyList())
    val photos: StateFlow<List<FlickrPhotoModel>> = _photos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchPhotos(
        apiKey: String?,
        searchText: String,
        tags: List<String>,
        matchType: MatchType,
        maxPhotos: Int,
        safeSearch: Boolean
    ) {
        viewModelScope.launch {
            try {
                _loading.value = true // Start loading
                val fetchedPhotos = repository.fetchPhotos(apiKey, searchText, tags, matchType, maxPhotos, safeSearch)
                _photos.value = fetchedPhotos
            } catch (e: Exception) {
                // Handle exceptions
            } finally {
                _loading.value = false // Stop loading
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
            try {
                _loading.value = true
                val fetchedPhotos = repository.fetchPhotosByUsername(apiKey, username, maxPhotos, safeSearch)
                _photos.value = fetchedPhotos
            } catch (e: Exception) {

            } finally {
                _loading.value = false
            }
        }
    }

    fun clearPhotos() {
        _photos.value = emptyList()
    }
}

