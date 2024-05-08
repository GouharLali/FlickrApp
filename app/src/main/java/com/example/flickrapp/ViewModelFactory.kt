package com.example.flickrapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flickrapp.data.FlickrRepository
import com.example.flickrapp.viewModel.FlickrViewModel

class ViewModelFactory(private val application: Application, private val repository: FlickrRepository) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlickrViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlickrViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
