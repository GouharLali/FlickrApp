package com.example.flickrapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newlist.data.FlickrRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlickrViewModel : ViewModel() {
    private val repository = FlickrRepository()
    private val _photos = MutableStateFlow<List<FlickrPhotoModel>>(emptyList())
    val photos: StateFlow<List<FlickrPhotoModel>> = _photos

    fun fetchPhotos(apiKey: String?, searchText: String) {
        viewModelScope.launch {
            val fetchedPhotos = repository.fetchPhotos(apiKey, searchText)
            _photos.value = fetchedPhotos
        }
    }
}
