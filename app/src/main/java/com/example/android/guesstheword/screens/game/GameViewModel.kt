package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * ViewModel containing all the logic needed to run the game
 */
class GameViewModel : ViewModel() {

    // The current word
    // Теперь это реактивные данные, за изменениями которых будет наблюдать вочер
    // Observer в соответствующем фрагменте
    val word : LiveData<String>
        get() = _word
    // Инкапсулирую свойства внутри вью модел класса
    private val _word = MutableLiveData<String>()

    // The current score
    // Теперь это реактивные данные, за изменениями которых будет наблюдать вочер
    // Observer в соответствующем фрагменте
    val score : LiveData<Int>
        get() = _score
    // Инкапсулирую свойства внутри вью модел класса
    private val _score = MutableLiveData<Int>()

    // The list of diseases - the front of the list is the next word to guess
    private lateinit var diseasesList: MutableList<String>

    init {
        resetList()
        Log.i("GameViewModel", "GameViewModel list must be filled!, $diseasesList")
        nextWord()
        Log.i("GameViewModel", "GameViewModel viewed word is..., ${word.value}")
        // лайв данные надо проинициализировать, в самой переменной указывается только тип данных и по дефолту он null
        _score.value = 0
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
            _word.value = diseasesList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        // приращение лейв-даты осуществляется через сеттер класса
        // (в котлине это просто .value)
        // так как значение может быть null то используется такая конструкция
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}