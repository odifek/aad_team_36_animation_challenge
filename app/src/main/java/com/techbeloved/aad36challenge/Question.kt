package com.techbeloved.aad36challenge

import com.techbeloved.aad36challenge.api.OpenTrivia
import java.util.*

/**
 * Model of the question.
 * @param answers is a list of all answers including correct one and incorrect ones
 * @param question is the question statement
 * @param difficulty can be easy, medium or hard
 * @param category example, Geography, Entertainment, etc
 * @param type is the question type: multiple or true or false type of question
 */
class Question(
    val id: String,
    val category: String,
    val type: QuestionType,
    val difficulty: Difficulty,
    val question: String,
    val answers: List<String>,
    val correctAnswer: String
)

sealed class Difficulty(val title: String, val points: Int) {
    object Easy : Difficulty("Easy", 5)
    object Medium : Difficulty("Medium", 10)
    object Hard : Difficulty("Hard", 20)
}

/**
 * Maps https://opentdb.com api results question to our app [Question]
 */
fun OpenTrivia.toQuestion(): Question {
    return Question(
        id = UUID.randomUUID().toString(),
        category = category,
        difficulty = when (difficulty) {
            "hard" -> Difficulty.Hard
            "medium" -> Difficulty.Medium
            else -> Difficulty.Easy
        },
        type = when (type) {
            "boolean" -> QuestionType.TRUE_FALSE
            else -> QuestionType.MULTIPLE
        },
        question = question,
        correctAnswer = correctAnswer,
        answers = (incorrectAnswers + correctAnswer).shuffled()
    )
}