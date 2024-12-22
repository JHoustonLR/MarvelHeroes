package com.example.marvel.api

data class MarvelCharacterResponse(
    val data: Data
)

data class Data(
    val results: List<Character>
)

data class Character(
    val id: Int,
    val name: String,
    val description: String?,
    val thumbnail: Thumbnail
)

data class Thumbnail(
    val path: String,
    val extension: String
)

