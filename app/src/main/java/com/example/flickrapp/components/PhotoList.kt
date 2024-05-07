package com.example.flickrapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.flickrapp.model.FlickrPhotoModel

@Composable
fun PhotoList(photos: List<FlickrPhotoModel>, navController: NavController) {

    LazyColumn {
        items(photos) { photo ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Owner ID: ${photo.owner}",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            navController.navigate("detail/${photo.owner}")
                        }
                )
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    if (photo.url_h != null) {
                        Image(
                            painter = rememberImagePainter(photo.url_h),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .clickable {
                                    navController.navigate("detail/${photo.owner}")
                                },
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color.LightGray)
                        )
                    }

                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        photo.tags?.let { tags ->
                            Text(
                                text = "Tags: ${tags.joinToString(", ")}",
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PhotoListPreview() {
    val samplePhotos = listOf(
        FlickrPhotoModel(
            id = "1",
            owner = "OwnerID1",
            secret = "abc123",
            server = "server1",
            farm = 1,
            title = "Sample Photo 1",
            isPublic = 1,
            isFriend = 0,
            isFamily = 0,
            datetaken = "2024-01-01",
            datetakengranularity = 0,
            datetakenunknown = "0",
            url_h = "https://via.placeholder.com/150",
            height_h = 150,
            width_h = 150,
            tags = listOf("tag1", "tag2")
        ),
        FlickrPhotoModel(
            id = "2",
            owner = "OwnerID2",
            secret = "def456",
            server = "server2",
            farm = 2,
            title = "Sample Photo 2",
            isPublic = 1,
            isFriend = 0,
            isFamily = 0,
            datetaken = "2024-01-02",
            datetakengranularity = 0,
            datetakenunknown = "0",
            url_h = null,
            height_h = 0,
            width_h = 0,
            tags = listOf("tag3", "tag4")
        )
    )

    val NavController = rememberNavController()

    Surface(color = Color.White) {
        PhotoList(photos = samplePhotos, navController = NavController)
    }
}

