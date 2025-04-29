package com.deliveryapp.deliverymodule.ui.account

import android.graphics.Color
import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {

    var color = Color.BLUE
        private set

    fun setColor(color: Int) {
        this.color = color
    }
}