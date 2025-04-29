package com.deliveryapp.authmodule.ui.codefragment.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal open class CodeViewModel : ViewModel() {
    private val baseTimer = 60

    private val _isTimerEnable = MutableLiveData(true)
    val isTimerEnable: LiveData<Boolean>
        get() = _isTimerEnable

    private val _resendTimer = MutableLiveData(baseTimer.toString())
    val resendTimer: LiveData<String>
        get() = _resendTimer

    init {
        startTimer()
    }

    private fun startTimer() {
        _isTimerEnable.value = true
        object : CountDownTimer(baseTimer.toLong() * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _resendTimer.value = millisUntilFinished.toString()
            }

            override fun onFinish() {
                _isTimerEnable.value = false
            }
        }

    }

}