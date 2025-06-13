package com.deliveryapp.deliverymodule.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deliveryapp.deliverymodule.domain.model.Card
import com.example.deliveryapp.databinding.FragmentPayCardsBinding
import com.example.deliveryapp.databinding.ItemCardBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CardFragment : Fragment() {

    private var _binding: FragmentPayCardsBinding? = null
    private val binding get() = _binding!!
    val accountViewModel: AccountViewModel by activityViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPayCardsBinding.inflate(inflater, container, false)

        accountViewModel.cards.observe(viewLifecycleOwner) { cards ->
            binding.cardsContainer.removeAllViews()
            cards.forEach { card ->
                val itemBinding = ItemCardBinding.inflate(layoutInflater, binding.cardsContainer, false)
                itemBinding.textCardNumber.text = card.number
                itemBinding.textExpiry.text = "Срок действия: ${card.date}"
                binding.cardsContainer.addView(itemBinding.root)
            }
        }

        binding.addCardBtn.setOnClickListener {
            val dialog = AddCardDialog()
            dialog.show(childFragmentManager, "AddCardDialog")
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.topBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}