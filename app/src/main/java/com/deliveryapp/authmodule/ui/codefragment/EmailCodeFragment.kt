package com.deliveryapp.authmodule.ui.codefragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deliveryapp.authmodule.ui.codefragment.viewmodel.SmsCodeViewModel
import com.deliveryapp.deliverymodule.ui.MainActivity
import com.example.deliveryapp.databinding.FragmentCodeEmailBinding

class EmailCodeFragment : Fragment() {
    private var _binding: FragmentCodeEmailBinding? = null
    private val binding get() = _binding!!
    private val smsCodeViewModel: SmsCodeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCodeEmailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueBtn.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}