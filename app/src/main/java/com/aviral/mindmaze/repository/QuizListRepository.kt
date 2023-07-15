package com.aviral.mindmaze.repository

import com.aviral.mindmaze.models.QuizListModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class QuizListRepository(
    onFirestoreTaskComplete: OnFirestoreTaskComplete
) {

    val onFirestoreTaskComplete = onFirestoreTaskComplete

    val firebaseFirestore = FirebaseFirestore.getInstance()

    val reference: CollectionReference = firebaseFirestore.collection("Quiz")

    fun getQuizData() {
        reference.get().addOnCompleteListener { task ->

            if (task.isSuccessful) {

                onFirestoreTaskComplete.onQuizDataLoaded(
                    task.result.toObjects(QuizListModel::class.java)
                )

            } else {
                task.exception?.let { onFirestoreTaskComplete.onError(it) }
            }

        }
    }


    interface OnFirestoreTaskComplete {

        fun onQuizDataLoaded(quizListModels: List<QuizListModel>)

        fun onError(e: Exception)

    }


}