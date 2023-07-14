package com.aviral.mindmaze.viewModels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aviral.mindmaze.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(
    private val application: Application
): AndroidViewModel(application) {


    private var authRepository = AuthRepository(application)

    private val firebaseUserMutableLiveData = authRepository.getFirebaseUserLiveData()

    fun signUp(email: String, password: String) {
        return authRepository.signUp(email, password)
    }

    fun signIn(email: String, password: String) {
        return authRepository.signIn(email, password)
    }

    fun signOut() {
        authRepository.signOut()
    }

    fun getFirebaseUserLiveData(): LiveData<FirebaseUser> {
        return firebaseUserMutableLiveData
    }


    fun getCurrentUser(): FirebaseUser {
        return authRepository.getCurrentUser()
    }

}