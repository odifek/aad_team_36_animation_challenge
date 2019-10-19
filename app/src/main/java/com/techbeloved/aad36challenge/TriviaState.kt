package com.techbeloved.aad36challenge

sealed class TriviaState {
    object Default: TriviaState()

    data class Play(val player: Player): TriviaState()

    data class Correct(val question: Question, val choice: String): TriviaState()

    data class Incorrect(val question: Question, val choice: String): TriviaState()

    /**
     * Should display summary of the game and
     */
    data class Summary(val player: Player): TriviaState()
}