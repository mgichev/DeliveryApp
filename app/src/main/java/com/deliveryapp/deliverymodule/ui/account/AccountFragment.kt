package com.deliveryapp.deliverymodule.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.deliveryapp.authmodule.ui.AuthActivity
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

        binding.statisticItem.root.setOnClickListener {
            findNavController().navigate(R.id.action_AccountFragment_to_fragmentInfoSalary)
        }

        binding.statisticItem.leftIcon.setImageResource(R.drawable.baseline_table_chart_24)
        binding.statisticItem.centerText.text = "Статистика"

        binding.settingsItem.leftIcon.setImageResource(R.drawable.baseline_settings_24)
        binding.settingsItem.centerText.text = "Настройки"

        binding.payItem.leftIcon.setImageResource(R.drawable.baseline_payment_24)
        binding.payItem.centerText.text = "Платежные данные"

        binding.personalItem.leftIcon.setImageResource(R.drawable.baseline_secure_maybe_24)
        binding.personalItem.centerText.text = "Персональная информация"

        binding.deliveryItem.leftIcon.setImageResource(R.drawable.baseline_delivery_type_24)
        binding.deliveryItem.centerText.text = "Параметры доставки"

        binding.supportItem.leftIcon.setImageResource(R.drawable.baseline_info_outline_24)
        binding.supportItem.centerText.text = "Техподдержка"

        binding.logoutBtn.setOnClickListener {
            val intent = Intent(context, AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}