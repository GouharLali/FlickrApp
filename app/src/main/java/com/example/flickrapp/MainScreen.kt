package com.example.newlist.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flickrapp.CenteredProgressBar
import com.example.flickrapp.FlickrViewModel
import com.example.flickrapp.PhotoList


@Composable
fun MainScreen(navController: NavController, viewModel: FlickrViewModel = viewModel()) {
    val photos by viewModel.photos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPhotos("53b0993fc69c9bea29e746e1a3a8806a", "Yorkshire")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Flickr",
                style = TextStyle(fontSize = 24.sp),
                modifier = Modifier.padding(vertical = 16.dp)
            )
            if (photos.isEmpty()) {
                CenteredProgressBar()
            } else {
                PhotoList(photos, navController)
            }
        }
    }
}