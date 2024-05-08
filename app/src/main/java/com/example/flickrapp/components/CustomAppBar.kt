package com.example.flickrapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flickrapp.R

@Composable
fun CustomAppBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "Back",
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Text(
            text = "Flickr",
            modifier = Modifier
                .padding(start = 5.dp)
                .align(Alignment.Center),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(id = R.drawable.dots),
            contentDescription = "Menu",
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterEnd)
        )
    }
}