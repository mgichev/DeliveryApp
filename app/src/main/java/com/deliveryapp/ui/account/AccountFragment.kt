package com.deliveryapp.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deliveryapp.domain.AccountData
import com.deliveryapp.domain.AccountStatistic
import com.deliveryapp.domain.Salary
import com.example.deliveryapp.databinding.FragmentAccountBinding


class AccountFragment: Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    val vm by viewModels<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        bindAccount(inflater)

        return binding.root
    }

    fun bindAccount(inflater: LayoutInflater) {
        val account = AccountData(0, "", "Name", listOf(), Salary("01-01-2500", "30000"),
            AccountStatistic("129312839129381938 часов", "21391823 часов", "25789 рублей", "300012 рублей")
        )

        val view = TextView(context)
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(24, 24,24, 24)
        view.text = "abcdef"
        view.layoutParams = layoutParams
        binding.salaryCards.addView(view)
        binding.accountData = account
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}