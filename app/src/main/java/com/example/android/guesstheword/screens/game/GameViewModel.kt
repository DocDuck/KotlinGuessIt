package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel


/**
 * ViewModel containing all the logic needed to run the game
 */
class GameViewModel : ViewModel() {

    // The current word
    var word = ""

    // The current score
    var score = 0

    // The list of diseases - the front of the list is the next word to guess
    private lateinit var diseasesList: MutableList<String>

    init {
        resetList()
        Log.i("GameViewModel", "GameViewModel list must be filled!, $diseasesList")
        nextWord()
        Log.i("GameViewModel", "GameViewModel viewed word is..., $word")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        diseasesList = mutableListOf(
                "амнезия",
                "ангина",
                "аппендицит",
                "бореллиоз",
                "БАС",
                "варикоз",
                "ветилиго",
                "волчанка",
                "ГЭРБ",
                "гастрит",
                "гепатит",
                "диарея",
                "дерматит",
                "деменция",
                "инфаркт",
                "инсульт",
                "иерсиниоз",
                "катаракта",
                "колит",
                "кариес",
                "лейкемия",
                "лепра",
                "ларингит",
                "меланома",
                "миозит",
                "малярия"
        )
        diseasesList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (diseasesList.isEmpty()) {
            // todo gameFinished()
        } else {
            word = diseasesList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        score--
        nextWord()
    }

    fun onCorrect() {
        score++
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}