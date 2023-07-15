package com.aviral.mindmaze.models

import com.google.firebase.firestore.DocumentId

data class QuestionModel(
    @DocumentId
    val questionId: String,
    val question: String,
    val answer: String,
    val option_a: String,
    val option_b: String,
    val option_c: String,
    val timer: Long
) {
    constructor(): this("", "", "", "", "", "", 0L)
}