package com.deliveryapp.deliverymodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.deliveryapp.databinding.FragmentInfoSalaryBinding

class FragmentInfoSalary : Fragment() {
    private var _binding: FragmentInfoSalaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoSalaryBinding.inflate(inflater, container, false)

        binding.btn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btn1.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.monthSalaryTv.centerText.text = "В этом месяце"
        binding.yearSalaryTv.centerText.text = "В этом году"
        binding.allSalaryTv.centerText.text = "Всего"

        binding.monthSalaryTv.valueText.text = "32 500 рублей"
        binding.yearSalaryTv.valueText.text = "91 524 рублей"
        binding.allSalaryTv.valueText.text = "400 254 рубля"

        binding.monthWorkTv.centerText.text = "В этом месяце"
        binding.yearWorkTv.centerText.text = "В этом году"
        binding.allWorkTv.centerText.text = "Всего"

        binding.monthWorkTv.valueText.text = "27 часов"
        binding.yearWorkTv.valueText.text = "129 часов"
        binding.allWorkTv.valueText.text = "762 часа"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}