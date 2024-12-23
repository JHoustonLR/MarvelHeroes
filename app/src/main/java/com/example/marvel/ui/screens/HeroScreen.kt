package com.example.marvel.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.marvel.R

@Composable
fun HeroScreen(
    modifier: Modifier = Modifier,
    url: String,
    name: String,
    description: String,
    onClose: () -> Unit
) {
    HeroScreenContent(
        modifier = modifier,
        imageUrl = url,
        name = name,
        description = description,
    )
}

@Composable
fun HeroScreenContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    description: String,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun HeroScreenPreview() {
    HeroScreenContent(
        imageUrl = "https://i.pinimg.com/originals/0e/a9/b4/0ea9b41396ca6ebdeba4848c31f6e030.jpg",
        name = "Hero",
        description = "Cool hero!"
    )
}
