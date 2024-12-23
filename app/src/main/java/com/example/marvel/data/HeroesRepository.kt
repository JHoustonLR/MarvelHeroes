package com.example.marvel.data

interface HeroesRepository {
    suspend fun getAllHeroes(): List<Superhero>

    suspend fun getHeroById(id: String): Superhero
}