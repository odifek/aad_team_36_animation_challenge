package com.techbeloved.aad36challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * All game decisions, retrieving questions, creating a new game and a new player will be done in this viewModel
 *
 * The activity displays whatever screen based on the state of the trivia.
 *
 * Also, all user inputs are forwarded here for decision making
 */
class TriviaViewModel: ViewModel() {

    private val _triviaState: MutableLiveData<TriviaState> = MutableLiveData(TriviaState.Default)

    /**
     * Observe this to receive the current state of the game
     */
    val triviaState: LiveData<TriviaState> = _triviaState
}