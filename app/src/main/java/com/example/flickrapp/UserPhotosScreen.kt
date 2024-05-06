package com.example.newlist.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flickrapp.CenteredProgressBar
import com.example.flickrapp.FlickrViewModel

@Composable
fun UserPhotosScreen(navController: NavController, ownerId: String, viewModel: FlickrViewModel = viewModel()) {
    val photos by viewModel.photos.collectAsState()

    DisposableEffect(ownerId) {
        viewModel.fetchPhotos("87bb0a115d2d86d33f1b75ee851f69af", "Yorkshire")
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