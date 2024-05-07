package com.example.flickrapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flickrapp.components.CenteredProgressBar
import com.example.flickrapp.components.PhotoList
import com.example.flickrapp.enumModel.MatchType
import com.example.flickrapp.viewModel.FlickrViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: FlickrViewModel = viewModel()) {
    val photos by viewModel.photos.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val usernameState = remember { mutableStateOf("") }
    val tagsState = remember { mutableStateOf("") }
    val matchTypeState = remember { mutableStateOf(MatchType.ALL) }

    // Default search for "Yorkshire"
    LaunchedEffect(Unit) {
        viewModel.fetchPhotos(
            apiKey = "3a07562a0586bef2236a925b0427ca0b",
            searchText = "Yorkshire",
            tags = emptyList(),
            matchType = MatchType.ALL,
            maxPhotos = Int.MAX_VALUE,
            safeSearch = true
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Flickr",
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Text field for entering username
            OutlinedTextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it },
                label = { Text("Username") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            // Text field for entering tags
            OutlinedTextField(
                value = tagsState.value,
                onValueChange = { tagsState.value = it },
                label = { Text("Tags") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            Row(modifier = Modifier.padding(8.dp)) {
                Text("Match Type: ")

                RadioButton(
                    selected = matchTypeState.value == MatchType.ALL,
                    onClick = { matchTypeState.value = MatchType.ALL },
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text("All")

                RadioButton(
                    selected = matchTypeState.value == MatchType.SOME,
                    onClick = { matchTypeState.value = MatchType.SOME },
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text("Some")
            }

            // Button to trigger the search by username or tags
            Button(
                onClick = {
                    val maxPhotos = if (matchTypeState.value == MatchType.ALL) {
                        Int.MAX_VALUE
                    } else {
                        5
                    }
                    if (usernameState.value.isNotBlank()) {
                        viewModel.fetchPhotosByUsername(
                            apiKey = "3a07562a0586bef2236a925b0427ca0b",
                            username = usernameState.value,
                            tags = tagsState.value.split(",").map { it.trim() },
                            maxPhotos = maxPhotos,
                            safeSearch = true
                        )
                    } else if (tagsState.value.isNotBlank()) {
                        viewModel.fetchPhotos(
                            apiKey = "3a07562a0586bef2236a925b0427ca0b",
                            searchText = "Yorkshire",
                            tags = tagsState.value.split(",").map { it.trim() },
                            matchType = matchTypeState.value,
                            maxPhotos = maxPhotos,
                            safeSearch = true
                        )
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(if (usernameState.value.isNotBlank()) "Search by Username" else "Search by Tags")
            }

            when {
                loading -> CenteredProgressBar()
                photos.isEmpty() && (usernameState.value.isNotBlank() || tagsState.value.isNotBlank()) -> Text("No photos found.")
                else -> PhotoList(photos, navController)
            }
        }
    }
}
