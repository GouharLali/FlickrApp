package com.example.flickrapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.flickrapp.model.FlickrPhotoModel
import androidx.navigation.NavController
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.navigation.compose.rememberNavController
import com.example.flickrapp.components.CustomAppBar

@Composable
fun DetailScreen(
    navController: NavController,
    photos: List<FlickrPhotoModel>,
    ownerId: String,
    onImageClick: () -> Unit
) {
    Box {
        Column {
            CustomAppBar(navController)

            if (photos.isEmpty()) {
                Text(text = "No photos found for this user")
            } else {
                LazyColumn {
                    items(photos) { photo ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column {
                                Image(
                                    painter = rememberImagePainter(photo.url_h),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clickable {
                                            onImageClick()
                                        },
                                    contentScale = ContentScale.Crop
                                )

                                Column(
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(
                                        text = "Title: ${photo.title}",
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    Text(
                                        text = "Date Taken: ${photo.datetaken}",
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    Text(
                                        text = "Dimensions: ${photo.width_h} x ${photo.height_h}",
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    Text(
                                        text = "Public: ${if (photo.isPublic == 1) "Yes" else "No"}",
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                }
                            }
                        }
                        Divider(color = Gray.copy(alpha = 0.12f))

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    val photo = FlickrPhotoModel(
        id = "53695882964",
        owner = "12090378@N05",
        secret = "5f7fbaf9bc",
        server = "65535",
        farm = 66,
        title = "Laino guztien gainetik EXPLORE#1",
        isPublic = 1,
        isFriend = 0,
        isFamily = 0,
        datetaken = "2016-07-30 07:25:55",
        datetakengranularity = 0,
        datetakenunknown = "0",
        url_h = "https://live.staticflickr.com/65535/53695882964_5f7fbaf9bc_h.jpg",
        height_h = 1000,
        width_h = 1500,
        tags = emptyList()
    )

    val navController = rememberNavController()
    Surface(color = Color.White) {
        DetailScreen(
            navController = navController,
            photos = listOf(photo),
            ownerId = photo.owner,
            onImageClick = {}
        )
    }
}

