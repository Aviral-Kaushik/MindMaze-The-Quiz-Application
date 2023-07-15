package com.aviral.mindmaze.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aviral.mindmaze.models.QuestionModel
import com.aviral.mindmaze.models.QuizListModel
import com.aviral.mindmaze.repository.QuestionRepository

class QuestionViewModel : ViewModel(), QuestionRepository.OnQuestionLoaded,
    QuestionRepository.OnResultAdded, QuestionRepository.OnResultLoaded {

    private val tag = "AvirelFirebase"

    private val questionLiveData = MutableLiveData<List<QuestionModel>>()

    private val resultLiveData = MutableLiveData<HashMap<String, Int>>()

    private val questionRepository = QuestionRepository(
        this,
        this,
        this
    )

    fun setQuizId(quizId: String) {
        questionRepository.setQuizId(quizId)
    }

    fun getQuestions() {
        questionRepository.getQuestions()
    }

    override fun onLoad(questionModels: List<QuestionModel>) {

        questionLiveData.value = questionModels

    }

    override fun onSubmit(): Boolean {
        return true
    }

    fun getResult() {
        questionRepository.getResult()
    }
    fun getResultLiveData(): LiveData<HashMap<String, Int>> {
        return resultLiveData
    }

    override fun onResultLoaded(resultMap: HashMap<String, Int>) {
        resultLiveData.value = resultMap
    }

    override fun onError(e: Exception) {
        Log.d(tag, "onError: Exception: ${e.message}")
    }

    fun getQuestionLiveData(): LiveData<List<QuestionModel>> {
        return questionLiveData
    }

    fun addResult(resultMap: HashMap<String, Int>) {
        questionRepository.addResult(resultMap)
    }

}