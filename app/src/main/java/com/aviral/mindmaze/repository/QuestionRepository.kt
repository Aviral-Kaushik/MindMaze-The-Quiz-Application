package com.aviral.mindmaze.repository

import androidx.lifecycle.MutableLiveData
import com.aviral.mindmaze.models.QuestionModel
import com.google.firebase.firestore.FirebaseFirestore

class QuestionRepository(
    private val onQuestionLoaded: OnQuestionLoaded
) {

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var quizId: String

    fun setQuizId(quizId: String) {
        this.quizId = quizId
    }

    fun getQuestions() {
        firebaseFirestore.collection("Quiz").document(quizId)
            .collection("questions").get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    onQuestionLoaded.onLoad(
                        task.result.toObjects(QuestionModel::class.java)
                    )

                } else {

                    task.exception?.let { onQuestionLoaded.onError(it) }

                }

            }
    }

    interface OnQuestionLoaded {

        fun onLoad(questionModels: List<QuestionModel>)

        fun onError(e: Exception)

    }

}