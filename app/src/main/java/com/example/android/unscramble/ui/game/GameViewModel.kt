package com.example.android.unscramble.ui.game
import android.util.Log
import androidx.lifecycle.ViewModel

/**
 * ViewModel yang berisikan data aplikasi dan metode untuk memproses data
 */
class GameViewModel : ViewModel(){
    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    // List of words used in the game
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    //ketikan proses onClear maka akan tampil pada log
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    /*
    * Memperbarui Word saat ini dan saat iniScrambledWord dengan kata berikutnya.
    */


    //membuat funsgi getNextWord
    private fun getNextWord() {
        //cuurentWord berisikan 1 data acak dari allWordList
        currentWord = allWordsList.random()
        //tempWord bersikan curentWord menjadikan array
        val tempWord = currentWord.toCharArray()
        //nilai dari tempWord dicak
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        //jika wordsList berisikan currentWord maka jalankan fungsi getNextWord
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    /*
    * Inisialisasi ulang data game untuk memulai ulang game.
    */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }

    /*
    * Inisialisasi ulang data game untuk memulai ulang game.
    */
    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    /*
    * Mengembalikan nilai true jika kata pemain benar.
    * Meningkatkan skor sesuai.
    */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /*
    * Mengembalikan nilai true jika jumlah kata saat ini kurang dari MAX_NO_OF_WORDS
    */
    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
}