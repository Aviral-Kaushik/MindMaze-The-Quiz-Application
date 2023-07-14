package com.aviral.mindmaze.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository(private var application: Application) {

    private val tag = "AviralFirebase"

    private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var currentUser: FirebaseUser

    fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                   firebaseUserMutableLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    Log.d(tag, "signUp: ${task.exception?.message}")
                    Toast.makeText(application, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUserMutableLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    Log.d(tag, "signUp: ${task.exception?.message}")
                    Toast.makeText(application, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun getFirebaseUserLiveData(): LiveData<FirebaseUser> {
        return firebaseUserMutableLiveData
    }


    fun getCurrentUser(): FirebaseUser {
        return currentUser
    }
}