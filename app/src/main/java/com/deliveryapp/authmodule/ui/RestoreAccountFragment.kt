package com.deliveryapp.authmodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentRestoreAccountBinding

class RestoreAccountFragment : Fragment() {

    private var _binding: FragmentRestoreAccountBinding? = null
    private val binding get() = _binding!!
    var isByPhoneNumber = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestoreAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueBtn.setOnClickListener {
//            val bundle = bundleOf("from" to "restore")
//            if(isByPhoneNumber)
//                findNavController().navigate(R.id.action_restoreAccountFragment_to_smsCodeFragment, bundle)
//            else
//                findNavController().navigate(R.id.action_restoreAccountFragment_to_emailCodeFragment, bundle)
        }

        binding.changeRestoreTypeBtn.setOnClickListener {
            isByPhoneNumber = !isByPhoneNumber
            if (isByPhoneNumber) {
                binding.changeRestoreTypeBtn.text = "Восстановить по телефону"
                binding.phoneTextField.hint = "Email"
            }
            else {
                binding.changeRestoreTypeBtn.text = "Восстановить по почте"
                binding.phoneTextField.hint = "Номер телефона"
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
}