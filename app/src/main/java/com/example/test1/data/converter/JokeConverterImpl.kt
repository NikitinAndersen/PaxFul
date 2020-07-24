package com.example.test1.data.converter

import com.example.test1.data.db.SharedPreferenceManager
import com.example.test1.data.db.joke.JokesEntity
import com.example.test1.data.model.Joke

class JokeConverterImpl(
    private val sharedPrefs: SharedPreferenceManager
) : JokeConverter {

    override fun convertJokeEntityToJoke(jokesEntity: JokesEntity): Joke {
        return Joke(id = jokesEntity.id, joke = jokesEntity.joke)
    }

    override fun convertJokeToJokeEntity(joke: Joke, isLiked: Boolean): JokesEntity {
        return JokesEntity(
            id = joke.id,
            joke = joke.joke,
            isLiked = isLiked
        )
    }

    override fun changeNameToCustom(item: Joke): Joke {
        val firstName = sharedPrefs.getFirstName()
        val lastName = sharedPrefs.getLastName()
        val jokeText = item.joke
        val newJokeText: String
        if (jokeText.contains(DEFAULT_FIRST_NAME, ignoreCase = true)
                .or(jokeText.contains(DEFAULT_LAST_NAME, ignoreCase = true))
        ) {
            if (!firstName.isBlank()) {
                if (!lastName.isBlank()) {
                    newJokeText = jokeText.replace(
                        DEFAULT_FIRST_NAME,
                        firstName,
                        ignoreCase = true
                    )
                        .replace(DEFAULT_LAST_NAME, lastName, ignoreCase = true)
                    return Joke(item.id, newJokeText, item.isLiked)
                }
                newJokeText = item.joke.replace(
                    DEFAULT_FIRST_NAME,
                    firstName,
                    ignoreCase = true
                )
                return Joke(item.id, newJokeText, item.isLiked)
            } else {
                newJokeText = replaceTextName(item.joke, DEFAULT_LAST_NAME, lastName)
                return Joke(item.id, newJokeText, item.isLiked)
            }
        }
        return item
    }

    private fun replaceTextName(
        textToChange: String,
        defaultName: String,
        newName: String
    ): String {
        return textToChange.replace(
            defaultName,
            newName,
            ignoreCase = true
        )
    }

    companion object {
        const val DEFAULT_FIRST_NAME = "Chuck"
        const val DEFAULT_LAST_NAME = "Norris"
    }
}