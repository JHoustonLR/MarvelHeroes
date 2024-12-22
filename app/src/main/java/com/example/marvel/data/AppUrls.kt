package com.example.marvel.data
import com.example.marvel.R


data class HeroData(
    val imageUrl: String,
    val nameResId: Int,
    val descriptionResId: Int
)

object AppUrls {
    const val MARVEL_LOGO_URL = "https://iili.io/JMnuvbp.png"

    val heroes = listOf(
        HeroData(
            imageUrl = "https://iili.io/JMnuyB9.png",
            nameResId = R.string.hero_name_spider_man,
            descriptionResId = R.string.hero_description_spider_man
        ),
        HeroData(
            imageUrl = "https://iili.io/JMnuDI2.png",
            nameResId = R.string.hero_name_iron_man,
            descriptionResId = R.string.hero_description_iron_man
        ),
        HeroData(
            imageUrl = "https://iili.io/JMnAfIV.png",
            nameResId = R.string.hero_name_deadpool,
            descriptionResId = R.string.hero_description_deadpool
        )
    )
}
