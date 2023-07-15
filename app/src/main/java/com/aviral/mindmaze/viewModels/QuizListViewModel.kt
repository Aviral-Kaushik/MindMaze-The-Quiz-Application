package com.aviral.mindmaze.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aviral.mindmaze.models.QuizListModel
import com.aviral.mindmaze.repository.QuizListRepository

class QuizListViewModel: ViewModel(), QuizListRepository.OnFirestoreTaskComplete {

    private val tag = "AvirelFirebase"

    private val quizListLiveData = MutableLiveData<List<QuizListModel>>()

    private val repository = QuizListRepository(this)

    init {
        repository.getQuizData()
    }

    override fun onQuizDataLoaded(quizListModels: List<QuizListModel>) {
        quizListLiveData.value = quizListModels
    }

    override fun onError(e: Exception) {

        Log.d(tag, "onError: Exception: ${e.message}")

    }

    fun getQuizLiveData(): LiveData<List<QuizListModel>> {
        return quizListLiveData
    }

}