package com.deliveryapp.deliverymodule.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deliveryapp.deliverymodule.domain.model.AccountData
import com.deliveryapp.deliverymodule.domain.model.AccountStatistic
import com.deliveryapp.deliverymodule.domain.model.Salary
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentAccountBinding


class AccountFragment: Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    val accountViewModel by viewModels<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        bindAccount(   )

        return binding.root
    }

    fun bindAccount() {
        val account = AccountData(0, "", "Name", listOf(), Salary("25 апреля", "42839 рублей"),
            AccountStatistic("37 часов", "21391823 часов", "37892 рублей", "300 012 рублей")
        )

        binding.statisticItem.leftIcon.setImageResource(R.drawable.baseline_stacked_bar_chart_24)
        binding.statisticItem.centerText.text = "Статистика"

        binding.settingsItem.leftIcon.setImageResource(R.drawable.drawable_settings_applications_24)
        binding.settingsItem.centerText.text = "Настройки"


    }

    fun addCard() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}