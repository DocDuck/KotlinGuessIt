package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * ViewModel containing all the logic needed to run the game
 */
class GameViewModel : ViewModel() {

    // Инкапсулирую изменяемые сойства внутри вью модел класса
    private val _word = MutableLiveData<String>()
    val word : LiveData<String>
        get() = _word

    // Инкапсулирую измениямые свойства внутри вью модел класса
    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score

    // The list of diseases - the front of the list is the next word to guess
    private lateinit var diseasesList: MutableList<String>

    // Событие, которое передает в фрагмент состояние конец игры
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    // Готовим поле для таймера
    private val timer: CountDownTimer

    // Выводим наружу лайв-дату со счетчиком, как обчно через теневое свойство
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    init {
        resetList()
        nextWord()
        // лайв данные надо проинициализировать, в самой переменной указывается только тип данных и по дефолту он null
        _score.value = 0
        // Создаем таймер, который меняет переменную каждый тик и останавливается когда срабатывает флаг eventGameFinish
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                // когда таймер кончится - число станет нулем и сработает флаг конца игры
                // с последующим переходом на фрагмент конца игры
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }

        timer.start()
    }

    companion object {
        // В котлине это аналог явовских static final, внешних констант
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
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
            // так как теперь игра на время, то лист перемешивается когда кончится
            resetList()
        }
        _word.value = diseasesList.removeAt(0)
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

    // Сбрасываем таймер после перезапуска приложения
    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    // этот метод очищает флаг, чтобы при ререндеринге фрагмента
    // не происходило повторно событие "конец игры" если оно уже произошло
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }
}