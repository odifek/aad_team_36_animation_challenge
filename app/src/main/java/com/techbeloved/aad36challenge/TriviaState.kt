package com.techbeloved.aad36challenge

sealed class TriviaState {
    object Default : TriviaState()

    data class Play(val question: Question, val index: Int, val total: Int) : TriviaState()

    sealed class GameOn(open val hasMore: Boolean) : TriviaState() {

        data class Correct(
            val question: Question,
            val choice: String,
            override val hasMore: Boolean
        ) : GameOn(hasMore)

        data class Incorrect(val question: Question, val choice: String, override val hasMore: Boolean) :
            GameOn(hasMore)

        data class Timeout(val question: Question, override val hasMore: Boolean) : GameOn(hasMore)

    }

    /**
     * Contains the summary of the game and how many points won by the player
     */
    data class Summary(val player: Player) : TriviaState()
}