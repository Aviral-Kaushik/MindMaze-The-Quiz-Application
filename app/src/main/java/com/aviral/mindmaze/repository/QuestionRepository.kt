package com.aviral.mindmaze.repository

import androidx.lifecycle.MutableLiveData
import com.aviral.mindmaze.models.QuestionModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class QuestionRepository(
    private val onQuestionLoaded: OnQuestionLoaded,
    private val onResultAdded: OnResultAdded,
    private val onResultLoaded: OnResultLoaded
) {

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var quizId: String

    private var resultMap = HashMap<String, Int>()

    private var currentUserId: String = FirebaseAuth.getInstance().currentUser!!.uid

    fun setQuizId(quizId: String) {
        this.quizId = quizId
    }

    fun getResult() {

        firebaseFirestore.collection("Quiz").document()
            .collection("results").document(currentUserId)
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    resultMap["correct"] = task.result.getLong("correct")?.toInt() ?: 1
                    resultMap["wrong"] = task.result.getLong("wrong")?.toInt() ?: 1
                    resultMap["notAnswered"] = task.result.getLong("notAnswered")?.toInt() ?: 1

                    onResultLoaded.onResultLoaded(resultMap)

                } else {

                    task.exception?.let { onResultLoaded.onError(it) }

                }

            }

    }

    fun addResult(resultMap: HashMap<String, Int>) {

        firebaseFirestore.collection("Quiz").document()
            .collection("results").document(currentUserId)
            .set(resultMap)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    onResultAdded.onSubmit()

                } else {

                    task.exception?.let { onResultAdded.onError(it) }

                }

            }

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

    interface OnResultAdded {

        fun onSubmit(): Boolean

        fun onError(e: Exception)

    }

    interface OnResultLoaded {

        fun onResultLoaded(resultMap: HashMap<String, Int>)

        fun onError(e: Exception)

    }

}