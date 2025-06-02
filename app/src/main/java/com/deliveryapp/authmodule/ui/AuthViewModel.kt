package com.deliveryapp.authmodule.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class AuthViewModel : ViewModel() {

    val successfulRegistration = MutableLiveData<Boolean>()
    val successfulLogin = MutableLiveData<Boolean>()
    val unsuccessfulAuthAction = MutableLiveData<String>()

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    fun createNewUser(email: String, password: String) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                successfulRegistration.postValue(true)
            }.addOnFailureListener { it ->
                val errorMessage = when (it) {
                    is FirebaseAuthUserCollisionException -> "Пользователь с таким email уже существует"
                    is FirebaseAuthInvalidCredentialsException -> "Неверный формат email"
                    else -> "Ошибка регистрации: ${it.localizedMessage}"
                }
                unsuccessfulAuthAction.postValue(errorMessage)
            }
        } catch (ex: Exception) {
            unsuccessfulAuthAction.postValue("Ошибка авторизации")
        }
    }

    fun login(email: String, password: String) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    successfulLogin.postValue(true)
                }
                .addOnFailureListener { exception ->
                    val errorMessage = when (exception) {
                        is FirebaseAuthInvalidCredentialsException -> "Неверный email или пароль"
                        else -> "Ошибка входа: ${exception.localizedMessage}"
                    }
                    unsuccessfulAuthAction.postValue(errorMessage)
                }
        } catch (e: Exception) {
            unsuccessfulAuthAction.postValue("Непредвиденная ошибка: ${e.localizedMessage}")
        }
    }

}