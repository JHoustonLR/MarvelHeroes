package com.example.marvel.presentation

import com.example.marvel.data.Superhero

sealed class HeroState {
    object Loading : HeroState()
    data class Success(val superhero: Superhero) : HeroState()
    object Error : HeroState()
}