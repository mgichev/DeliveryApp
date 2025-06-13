package com.deliveryapp.deliverymodule.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deliveryapp.deliverymodule.domain.model.Card
import com.deliveryapp.deliverymodule.domain.model.Category
import com.deliveryapp.deliverymodule.domain.model.PersonalInfo
import com.deliveryapp.deliverymodule.domain.model.Statistic
import com.deliveryapp.deliverymodule.domain.repository.LogRepository
import com.deliveryapp.deliverymodule.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(
    val logRepository: LogRepository,
    val userRepository: UserRepository
) : ViewModel() {


    private val _selectedCategories = MutableLiveData<Set<Category>>(emptySet())
    val selectedCategories: LiveData<Set<Category>> get() = _selectedCategories


    private val _cards = MutableLiveData<List<Card>>(emptyList())
    val cards: LiveData<List<Card>> get() = _cards

    private val _personalInfo = MutableLiveData<PersonalInfo?>()
    val personalInfo: LiveData<PersonalInfo?> get() = _personalInfo

    private val _statistic = MutableLiveData<Statistic>()
    val statistic: LiveData<Statistic> get() = _statistic

    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "test"

    init {
//        _statistic.value = Statistic(
//            salaryMonth = "32 500 рублей",
//            salaryYear = "91 524 рублей",
//            salaryTotal = "400 254 рубля",
//            workMonth = "27 часов",
//            workYear = "129 часов",
//            workTotal = "762 часа"
//        )
//
//        updateCards(
//            listOf(
//                Card("VISA **** 1234", "12/25"),
//                Card("MasterCard **** 5678", "09/26"),
//                Card("МИР **** 0001", "11/23")
//            )
//        )
        loadUserData()
    }

    val logInfo = MutableLiveData<String>()

    fun updateStatistic(stat: Statistic) {
        _statistic.value = stat
    }

    fun logData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                logRepository.log()
                logInfo.postValue("Данные успешно отправлены")
            } catch (_: Exception) {
                logInfo.postValue("Сервер для логгирования недоступен")
            }
        }
    }

    fun toggleCategory(category: Category, isSelected: Boolean) {
        val current = _selectedCategories.value?.toMutableSet() ?: mutableSetOf()
        if (isSelected) {
            current.add(category)
        } else {
            current.remove(category)
        }
        _selectedCategories.value = current

        viewModelScope.launch {
            try {
                userRepository.saveSelectedCategories(userId, current)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePersonalInfo(info: PersonalInfo) {
        _personalInfo.value = info
    }

    fun updateCards(newCards: List<Card>) {
        _cards.value = newCards
    }

    fun loadUserData() {
        viewModelScope.launch {
            try {
                val info = userRepository.getPersonalInfo(userId)
                _personalInfo.postValue(info)

                val stat = userRepository.getStatistic(userId)
                stat?.let { _statistic.postValue(it) }

                val cardsList = userRepository.getCards(userId)
                _cards.postValue(cardsList)

                val categories = userRepository.getSelectedCategories(userId)
                _selectedCategories.postValue(categories)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}