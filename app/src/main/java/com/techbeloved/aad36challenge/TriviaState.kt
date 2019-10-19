package com.techbeloved.aad36challenge

sealed class TriviaState {
    object Default: TriviaState()

    data class Play(val player: Player): TriviaState()

    data class Correct(val question: Question, val choice: String, val hasMore: Boolean): TriviaState()

    data class Incorrect(val question: Question, val choice: String, val hasMore: Boolean): TriviaState()

    data class Timeout(val question: Question, val hasMore: Boolean): TriviaState()

    /**
     * Contains the summary of the game and how many points won by the player
     */
    data class Summary(val player: Player): TriviaState()
}