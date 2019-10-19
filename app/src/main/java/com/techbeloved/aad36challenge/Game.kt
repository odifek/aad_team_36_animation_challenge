package com.techbeloved.aad36challenge

/**
 * @param choices is a map of answered questions (their ids) and the answer selected by the player, or timeout
 */
data class Game(
    val id: String,
    val questions: List<Question>,
    var completed: Boolean,
    val choices: MutableMap<String, String>
)