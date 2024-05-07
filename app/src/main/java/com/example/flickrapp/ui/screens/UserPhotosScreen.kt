package com.example.flickrapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flickrapp.components.CenteredProgressBar
import com.example.flickrapp.enumModel.MatchType
import com.example.flickrapp.viewModel.FlickrViewModel

@Composable
fun UserPhotosScreen(navController: NavController, ownerId: String, viewModel: FlickrViewModel = viewModel()) {
    val photos by viewModel.photos.collectAsState()
    val tags by remember { mutableStateOf("") }
    val matchType by remember { mutableStateOf(MatchType.ALL) }

    DisposableEffect(ownerId) {
        val maxPhotos = if (matchType == MatchType.SOME) 6 else Int.MAX_VALUE // Limit to 6 photos if match type is SOME
        viewModel.fetchPhotos(
            apiKey = "015e5180cca909b09a30df9492f91ae4",
            searchText = "Yorkshire",
            tags = tags.split(",").map { it.trim() }, // Split and trim tags entered by the user
            matchType = matchType,
            maxPhotos = maxPhotos,
            safeSearch = true,
        )
        onDispose { }
    }

    Surface(color = Color.White) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (photos.isEmpty()) {
                CenteredProgressBar()
            } else {
                DetailScreen(
                    photos = photos,
                    ownerId = ownerId,
                    onImageClick = {
                        navController.navigate("detail/$ownerId")
                    }
                )
            }
        }
    }
}
