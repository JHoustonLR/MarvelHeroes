package com.example.marvel

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marvel.presentation.MainViewModel

@Composable
fun MarvelApp() {
    val viewModel: MainViewModel = viewModel()  // Получаем viewModel внутри компонента
}