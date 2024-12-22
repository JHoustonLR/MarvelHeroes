package com.example.marvel.api

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.marvel.R


@Composable
fun CharacterImage(characterId: Int) {
    val viewModel: CharacterViewModel = viewModel()
    val imageUrl by viewModel.characterImageUrl.collectAsState() // Используем collectAsState для наблюдения за состоянием
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(characterId) {
        val apiKey = "MARVEL_API_KEY"
        val privateKey = "MARVEL_PRIVATE_KEY"
        viewModel.getCharacterImage(characterId, apiKey, privateKey)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Marvel character image",
                modifier = Modifier.fillMaxSize(),
                error = painterResource(id = R.drawable.sholk),
                onError = { painterState ->
                    val throwable = painterState.result.throwable
                    if (throwable != null) {
                        Log.e("CharacterImage", "Ошибка загрузки изображения: ${throwable.message}")
                    } else {
                        Log.e("CharacterImage", "Неизвестная ошибка загрузки изображения")
                    }
                },
                onSuccess = { painterState ->
                    Log.d("CharacterImage", "Изображение успешно загружено: ${painterState.result}")
                }
            )
        } else if (errorMessage != null) {
            Text(text = "Ошибка: $errorMessage")
        } else {
            Text(text = "Загружается изображение...")
        }
    }
}