package com.example.marvel.data

import com.example.marvel.api.MarvelApiService
import com.example.marvel.data.Mappers.Companion.mapToSuperHeroEntity
import com.example.marvel.data.Mappers.Companion.mapToSuperhero
import com.example.marvel.data.Mappers.Companion.mapToSuperheroes
import com.example.marvel.data.database.SuperheroEntity
import com.example.marvel.data.database.SuperheroDao
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

private const val PUBLIC_API_KEY = "ffcbb0906e8b30f93686712b3a023956"
private const val PRIVATE_API_KEY = "e138cb0628f33887dc7c6968e4c08f8d4f2867e1"
private const val MILLIS_IN_SECOND = 1000L

class HeroesRepositoryImpl @Inject constructor(
    private val api: MarvelApiService,
    private val dao: SuperheroDao,
) : HeroesRepository {

    override suspend fun getAllHeroes(): List<Superhero> {
        val cachedData = dao.getAll()
        if (cachedData.isEmpty()) {
            val networkData = getAllHeroesFromNetwork()
            dao.insert(networkData.map { hero -> mapToSuperHeroEntity(hero) })
            return networkData
        }

        return cachedData.map { heroEntity: SuperheroEntity -> mapToSuperhero(heroEntity) }
    }

    override suspend fun getHeroById(id: String): Superhero {
        val cachedData = dao.getById(id)
        if (cachedData == null) {
            val networkData = getHeroByIdFromNetwork(id)
            dao.insert(mapToSuperHeroEntity(networkData))
            return networkData
        }

        return mapToSuperhero(cachedData)
    }

    private suspend fun getAllHeroesFromNetwork(): List<Superhero> {
        val timestamp = getCurrentTimestamp()
        val response = api.getCharacters(
            apiKey = PUBLIC_API_KEY,
            ts = timestamp,
            hash = getHash(timestamp),
        )

        val charactersResponse = response.body() ?: throw Exception("Failed to fetch data")
        return Mappers.mapToSuperheroes(charactersResponse)
    }

    private suspend fun getHeroByIdFromNetwork(id: String): Superhero {
        val timestamp = getCurrentTimestamp()
        val response = api.getCharacter(
            characterId = id.toInt(),
            apiKey = PUBLIC_API_KEY,
            ts = timestamp,
            hash = getHash(timestamp),
        )

        val characterResponse = response.body() ?: throw Exception("Failed to fetch data")
        return Mappers.mapToSuperheroes(characterResponse).firstOrNull()
            ?: throw Exception("Hero not found")
    }


    private fun getHash(timestamp: Long): String {
        return md5("$timestamp$PRIVATE_API_KEY$PUBLIC_API_KEY")
    }

    private fun getCurrentTimestamp(): Long {
        return System.currentTimeMillis() / MILLIS_IN_SECOND
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }
}
