package com.aviral.mindmaze.models

import com.google.firebase.firestore.DocumentId

data class QuizListModel(
    @DocumentId
    val quizId: String,
    val title: String,
    val questions: Long,
    val difficulty: String,
    val image: String
)
