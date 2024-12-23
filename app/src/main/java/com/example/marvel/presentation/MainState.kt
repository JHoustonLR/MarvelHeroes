package com.example.marvel.presentation

import com.example.marvel.data.Superhero

sealed class MainState {

    data object Loading : MainState()

    data class Success(
        val superheroes: List<Superhero>,
    ) : MainState()

    data object Error : MainState()
}