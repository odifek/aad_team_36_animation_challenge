package com.techbeloved.aad36challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.techbeloved.aad36challenge.api.OpenTriviaResults
import com.techbeloved.aad36challenge.api.SAMPLE_RESPONSE
import timber.log.Timber
import java.util.*

/**
 * All game decisions, retrieving questions, creating a new game and a new player will be done in this viewModel
 *
 * The activity displays whatever screen based on the state of the trivia.
 *
 * Also, all user inputs are forwarded here for decision making
 */
class TriviaViewModel : ViewModel() {

    private var _currentQuestion: Question? = null
    private val _triviaState: MutableLiveData<TriviaState> = MutableLiveData(TriviaState.Default)

    private var _currentPlayer: Player? = null
    private var _nextQuestionIndex = 0

    /**
     * Observe this to receive the current state of the game
     */
    val triviaState: LiveData<TriviaState> = _triviaState

    fun startNewGame(playerName: String) {

        val gson = Gson()
        try {
            // TODO: Get trivia from api
            val openTriviaResults: OpenTriviaResults =
                gson.fromJson(SAMPLE_RESPONSE, OpenTriviaResults::class.java)
            if (openTriviaResults.responseCode == 0) {
                val newGame = Game(
                    id = UUID.randomUUID().toString(),
                    completed = false,
                    choices = mutableMapOf(),
                    questions = openTriviaResults.results.map { trivia -> trivia.toQuestion() }
                )
                _currentPlayer = Player(
                    name = playerName,
                    game = newGame,
                    score = 0
                )
                val nextQuestion = selectNextQuestion()
                if (nextQuestion != null) {
                    dispatchNextQuestion(nextQuestion)
                }
            } else {
                Timber.w("Error getting getting questions")

            }
        } catch (e: JsonParseException) {
            Timber.w(e, "Error parsing response!")
        }

    }

    /**
     * Called with the answer selected by the player
     * @param answer The choice made by the player
     */
    fun selectedAnswer(answer: String) {
        _currentQuestion?.let { question ->
            _currentPlayer?.let { player ->
                val hasMore = selectNextQuestion() != null
                val nextState = if (question.correctAnswer == answer) {
                    player.score.plus(question.difficulty.points)
                    TriviaState.GameOn.Correct(question, answer, hasMore)
                } else {
                    TriviaState.GameOn.Incorrect(question, answer, hasMore)
                }
                player.game.choices[question.id] = answer
                player.game.completed = !hasMore
                _triviaState.value = nextState
            }
        }
    }

    private fun selectNextQuestion(): Question? {
        return _currentPlayer?.let { player ->
            if (_nextQuestionIndex < player.game.questions.size) player.game.questions[_nextQuestionIndex] else {
                Timber.i("No more questions to play")
                null
            }
        }
    }

    private fun dispatchNextQuestion(question: Question) {
        _currentQuestion = question
        val questionCount = _currentPlayer?.game?.questions?.count() ?: 0
        _triviaState.value = TriviaState.Play(question, _nextQuestionIndex + 1, questionCount) // 1 base index
        _nextQuestionIndex++
    }

    fun next() {
        when (val state = _triviaState.value) {
            is TriviaState.GameOn -> {
                val nextQuestion = selectNextQuestion()
                if (state.hasMore && nextQuestion != null) {
                    dispatchNextQuestion(nextQuestion)
                } else {
                    _currentPlayer?.let { player ->
                        _triviaState.value = TriviaState.Summary(player)
                    }
                }
            }
        }
    }
}