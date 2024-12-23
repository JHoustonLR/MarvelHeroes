package com.example.marvel.data

import com.example.marvel.api.MarvelCharacterResponse
import com.example.marvel.data.database.SuperheroEntity


class Mappers {
    companion object {
        fun mapToSuperheroes(charactersResponse: MarvelCharacterResponse): List<Superhero> {
            return charactersResponse.data.results.map { hero ->
                val imagePath = if (hero.thumbnail.path.startsWith("http://")) {
                    hero.thumbnail.path.replace("http", "https")
                } else {
                    hero.thumbnail.path
                }

                Superhero(
                    id = hero.id.toString(),
                    imageUrl = "$imagePath.${hero.thumbnail.extension}",
                    name = hero.name,
                    description = hero.description.orEmpty(),
                )
            }
        }

        fun mapToSuperhero(entity: SuperheroEntity): Superhero {
            return Superhero(
                id = entity.id,
                imageUrl = entity.imageUrl,
                name = entity.name,
                description = entity.description,
            )
        }

        fun mapToSuperHeroEntity(superhero: Superhero): SuperheroEntity {
            return SuperheroEntity(
                id = superhero.id,
                name = superhero.name,
                description = superhero.description,
                imageUrl = superhero.imageUrl,
            )
        }
    }
}