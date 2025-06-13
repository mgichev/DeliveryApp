package com.deliveryapp.deliverymodule.ui.account

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.deliveryapp.deliverymodule.domain.model.Card
import com.example.deliveryapp.databinding.DialogAddCardBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class AddCardDialog : DialogFragment() {

    private var _binding: DialogAddCardBinding? = null
    private val binding get() = _binding!!
    private val accountViewModel: AccountViewModel by activityViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddCardBinding.inflate(layoutInflater)

        return AlertDialog.Builder(requireContext())
            .setTitle("Добавить карту")
            .setView(binding.root)
            .setPositiveButton("Добавить") { _, _ ->
                val number = binding.editCardNumber.text.toString()
                val date = binding.editCardExpiry.text.toString()
                var list = accountViewModel.cards.value ?: listOf()
                list = list + Card(number, date)
                accountViewModel.updateCards(
                    list
                )

                // TODO: передать данные через callback, ViewModel и т.д.
            }
            .setNegativeButton("Отмена", null)
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}