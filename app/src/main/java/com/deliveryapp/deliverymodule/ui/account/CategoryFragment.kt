package com.deliveryapp.deliverymodule.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deliveryapp.deliverymodule.domain.model.Category
import com.example.deliveryapp.databinding.FragmentAccountCategoryBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment() {
    private var _binding: FragmentAccountCategoryBinding? = null
    private val binding get() = _binding!!
    val accountViewModel: AccountViewModel by activityViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountCategoryBinding.inflate(inflater, container, false)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.topBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        val checkboxMap: Map<Category, CheckBox> = mapOf(
            Category.FRIDGE to binding.checkboxFridge,
            Category.HEATER to binding.checkboxHeater,
            Category.FRAGILE to binding.checkboxFragile,
            Category.LARGE to binding.checkboxLarge,
            Category.SMALL to binding.checkboxSmall,
            Category.ORGANIC to binding.checkboxOrganic,
            Category.URGENT to binding.checkboxUrgent
        )

        checkboxMap.forEach { (category, checkbox) ->
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                accountViewModel.toggleCategory(category, isChecked)
            }
        }

        accountViewModel.selectedCategories.observe(viewLifecycleOwner) { selected ->
            checkboxMap.forEach { (category, checkbox) ->
                if (checkbox.isChecked != selected.contains(category)) {
                    checkbox.setOnCheckedChangeListener(null)
                    checkbox.isChecked = selected.contains(category)
                    checkbox.setOnCheckedChangeListener { _, isChecked ->
                        accountViewModel.toggleCategory(category, isChecked)
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}