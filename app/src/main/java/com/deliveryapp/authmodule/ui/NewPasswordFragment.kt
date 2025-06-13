package com.deliveryapp.authmodule.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deliveryapp.deliverymodule.ui.MainActivity
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentNewPasswordBinding

class NewPasswordFragment : Fragment() {
    private var _binding: FragmentNewPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val login = binding.loginTextField.editText?.text

        binding.continueBtn.setOnClickListener {
            findNavController().popBackStack(R.id.AuthFragment, false)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}