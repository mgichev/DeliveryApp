// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Класс для хранения состояния экрана страниц,
// связанных с настройкой и просмотром данных аккаунта.
// А также для взаимодействия с репозиторием пользовательски данных.

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

/**
 * ViewModel для экрана аккаунта пользователя.
 * Управляет данными пользователя: персональной информацией, статистикой,
 * платежными картами и выбранными категориями.
 *
 * @property logRepository Репозиторий для логирования действий
 * @property userRepository Репозиторий для работы с данными пользователя
 */
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
        loadUserData()
    }

    val logInfo = MutableLiveData<String>()

    /**
     * Обновляет статистику пользователя.
     * @param stat Новый объект статистики
     */
    fun updateStatistic(stat: Statistic) {
        _statistic.value = stat
    }


    /**
     * Отправляет логи на сервер.
     * Результат операции сохраняется в logInfo.
     */
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

    /**
     * Изменяет состояние (на основе чекбокса) выбранной категории.
     * @param category Категория для изменения
     * @param isSelected Флаг выбора (true - добавить, false - удалить)
     */
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

    /**
     * Обновляет персональную информацию.
     * @param info Новый объект PersonalInfo
     */
    fun updatePersonalInfo(info: PersonalInfo) {
        _personalInfo.value = info
    }

    /**
     * Обновляет список платежных карт.
     * @param newCards Новый список карт
     */
    fun updateCards(newCards: List<Card>) {
        _cards.value = newCards
    }

    /**
     * Загружает данные пользователя из репозитория.
     * Обновляет все LiveData поля: персональную информацию, статистику,
     * карты и выбранные категории.
     */
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