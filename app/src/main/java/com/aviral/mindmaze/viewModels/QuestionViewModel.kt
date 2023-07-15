package com.aviral.mindmaze.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aviral.mindmaze.models.QuestionModel
import com.aviral.mindmaze.models.QuizListModel
import com.aviral.mindmaze.repository.QuestionRepository

class QuestionViewModel: ViewModel(), QuestionRepository.OnQuestionLoaded {

    private val tag = "AvirelFirebase"

    private val questionLiveData = MutableLiveData<List<QuestionModel>>()

    private val questionRepository = QuestionRepository(this)

    init {
        questionRepository.getQuestions()
    }

    fun setQuizId(quizId: String) {
        questionRepository.setQuizId(quizId)
    }

    override fun onLoad(questionModels: List<QuestionModel>) {

        questionLiveData.value = questionModels

    }

    override fun onError(e: Exception) {
        Log.d(tag, "onError: Exception: ${e.message}")
    }

    fun getQuestionLiveData(): LiveData<List<QuestionModel>> {
        return questionLiveData
    }

}