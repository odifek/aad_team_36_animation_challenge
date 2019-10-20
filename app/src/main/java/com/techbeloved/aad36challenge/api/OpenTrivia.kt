package com.techbeloved.aad36challenge.api

import com.google.gson.annotations.SerializedName

/**
 * An open trivia db model (https://opentdb.com) for a single question
 */
data class OpenTrivia(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>
)

/**
 * Wrapper for the results returned from open trivia db
 */
data class OpenTriviaResults(@SerializedName("response_code") val responseCode: Int, val results: List<OpenTrivia>)


const val SAMPLE_RESPONSE =
    """{"response_code":0,"results":[{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"According to the International System of Units, how many bytes are in a kilobyte of RAM?","correct_answer":"1000","incorrect_answers":["512","1024","500"]},{"category":"Science: Computers","type":"multiple","difficulty":"medium","question":"What did the name of the Tor Anonymity Network orignially stand for?","correct_answer":"The Onion Router","incorrect_answers":["The Only Router","The Orange Router","The Ominous Router"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"Which computer language would you associate Django framework with?","correct_answer":"Python","incorrect_answers":["C#","C++","Java"]},{"category":"Science: Computers","type":"boolean","difficulty":"easy","question":"The Windows 7 operating system has six main editions.","correct_answer":"True","incorrect_answers":["False"]},{"category":"Science: Computers","type":"boolean","difficulty":"medium","question":"The first dual-core CPU was the Intel Pentium D.","correct_answer":"False","incorrect_answers":["True"]},{"category":"Science: Computers","type":"boolean","difficulty":"medium","question":"The last Windows operating system to be based on the Windows 9x kernel was Windows 98.","correct_answer":"False","incorrect_answers":["True"]},{"category":"Science: Computers","type":"multiple","difficulty":"medium","question":"Which programming language was developed by Sun Microsystems in 1995?","correct_answer":"Java","incorrect_answers":["Python","Solaris OS","C++"]},{"category":"Science: Computers","type":"multiple","difficulty":"hard","question":"Who invented the &quot;Spanning Tree Protocol&quot;?","correct_answer":"Radia Perlman","incorrect_answers":["Paul Vixie","Vint Cerf","Michael Roberts"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"Which programming language shares its name with an island in Indonesia?","correct_answer":"Java","incorrect_answers":["Python","C","Jakarta"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"How long is an IPv6 address?","correct_answer":"128 bits","incorrect_answers":["32 bits","64 bits","128 bytes"]}]}"""