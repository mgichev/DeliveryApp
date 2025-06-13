// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Страница для просмотра статистики пользователя.

package com.deliveryapp.deliverymodule.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.deliveryapp.databinding.FragmentInfoSalaryBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FragmentInfoSalary : Fragment() {
    private var _binding: FragmentInfoSalaryBinding? = null
    private val binding get() = _binding!!
    val accountViewModel: AccountViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoSalaryBinding.inflate(inflater, container, false)

        binding.btn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.topBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.monthSalaryTv.centerText.text = "В этом месяце"
        binding.yearSalaryTv.centerText.text = "В этом году"
        binding.allSalaryTv.centerText.text = "Всего"


        binding.monthWorkTv.centerText.text = "В этом месяце"
        binding.yearWorkTv.centerText.text = "В этом году"
        binding.allWorkTv.centerText.text = "Всего"

        accountViewModel.statistic.observe(viewLifecycleOwner) { stat ->
            binding.monthSalaryTv.valueText.text = stat.salaryMonth
            binding.yearSalaryTv.valueText.text = stat.salaryYear
            binding.allSalaryTv.valueText.text = stat.salaryTotal

            binding.monthWorkTv.valueText.text = stat.workMonth
            binding.yearWorkTv.valueText.text = stat.workYear
            binding.allWorkTv.valueText.text = stat.workTotal
        }

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