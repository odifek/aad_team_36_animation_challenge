package com.techbeloved.aad36challenge

/**
 * Model of the question.
 * @param answers is a list of all answers including correct one and incorrect ones
 * @param quiz is the question statement
 * @param difficulty can be easy, medium or hard
 * @param category example, Geography, Entertainment, etc
 * @param type is the question type: multiple or true or false type of question
 */
class Question(
    val id: String,
    val category: String,
    val type: QuestionType,
    val difficulty: String,
    val quiz: String,
    val answers: List<String>,
    val correctAnswer: String
)
