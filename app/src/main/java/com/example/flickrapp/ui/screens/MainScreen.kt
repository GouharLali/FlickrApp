package com.example.flickrapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flickrapp.components.CenteredProgressBar
import com.example.flickrapp.enumModel.MatchType
import com.example.flickrapp.components.PhotoList
import com.example.flickrapp.viewModel.FlickrViewModel
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect

@Composable
fun MainScreen(navController: NavController, viewModel: FlickrViewModel = viewModel()) {
    val photos by viewModel.photos.collectAsState()
    val loading by viewModel.loading.collectAsState()
    var tags by remember { mutableStateOf("") }
    var matchType by remember { mutableStateOf(MatchType.ALL) }

    // Default search for "Yorkshire"
    LaunchedEffect(Unit) {
        val defaultSearch = {
            if (tags.isBlank()) {
                viewModel.fetchPhotos(
                    apiKey = "015e5180cca909b09a30df9492f91ae4",
                    searchText = "Yorkshire",
                    tags = emptyList(),
                    matchType = MatchType.ALL,
                    maxPhotos = Int.MAX_VALUE,
                    safeSearch = true
                )
            }
        }
        defaultSearch.invoke()
    }

// Function to trigger the search when the button is clicked
    val onSearchClick: () -> Unit = {
        val maxPhotos = if (matchType == MatchType.ALL) {
            Int.MAX_VALUE // Show all photos if match type is ALL
        } else {
            5 // Show only 5 photos if match type is SOME
        }
        if (tags.isNotBlank()) {
            viewModel.fetchPhotos(
                apiKey = "53b0993fc69c9bea29e746e1a3a8806a",
                searchText = "Yorkshire", // Default search text
                tags = tags.split(",").map { it.trim() },
                matchType = matchType, // Use the currently selected match type
                maxPhotos = maxPhotos,
                safeSearch = true
            )
        } else {
            // If no tags entered, clear the photo list
            viewModel.clearPhotos()
        }
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

            // Text field for entering tags
            OutlinedTextField(
                value = tags,
                onValueChange = { tags = it },
                label = { Text("Tags") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Match Type: ")

                RadioButton(
                    selected = matchType == MatchType.ALL,
                    onClick = { matchType = MatchType.ALL },
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text("All")

                RadioButton(
                    selected = matchType == MatchType.SOME,
                    onClick = { matchType = MatchType.SOME },
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text("Some")
            }

            // Button to trigger the search
            Button(
                onClick = onSearchClick,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Search")
            }

            when {
                loading -> CenteredProgressBar()
                photos.isEmpty() && tags.isNotBlank() -> Text("No photos found.")
                else -> PhotoList(photos, navController)
            }
        }
    }
}
