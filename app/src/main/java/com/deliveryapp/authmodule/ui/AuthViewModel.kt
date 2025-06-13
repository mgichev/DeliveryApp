package com.deliveryapp.authmodule.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deliveryapp.deliverymodule.domain.User
import com.deliveryapp.deliverymodule.domain.model.PersonalInfo
import com.deliveryapp.deliverymodule.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val successfulRegistration = MutableLiveData<Boolean>()
    val successfulLogin = MutableLiveData<Boolean>()
    val unsuccessfulAuthAction = MutableLiveData<String>()

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user


    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    fun setUser(user: User) {
        _user.value = user
    }

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

    fun registerUser(userInfo: PersonalInfo) {
        viewModelScope.launch {
            try {
                userRepository.addUser(User(user.value?.uid ?: "test", user.value?.email ?: "", userInfo))
            } catch (e: Exception) {
            }
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