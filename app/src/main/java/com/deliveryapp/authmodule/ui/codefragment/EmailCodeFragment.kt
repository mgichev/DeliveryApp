package com.deliveryapp.authmodule.ui.codefragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.deliveryapp.authmodule.ui.AuthViewModel
import com.deliveryapp.authmodule.ui.codefragment.viewmodel.SmsCodeViewModel
import com.deliveryapp.deliverymodule.ui.MainActivity
import com.example.deliveryapp.databinding.FragmentCodeEmailBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class EmailCodeFragment : Fragment() {
    private var _binding: FragmentCodeEmailBinding? = null
    private val binding get() = _binding!!
    val authViewModel: AuthViewModel by activityViewModel()

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

        var otpFields: List<EditText> = listOf()
        with(binding) {
            otpFields = arrayOf(otp1, otp2, otp3, otp4, otp5, otp6).toList()
        }

        otpFields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.length == 1 && index < otpFields.size - 1) {
                        otpFields[index + 1].requestFocus()
                    }

                    if (p0.isNullOrEmpty() && index > 0) {
                        otpFields[index - 1].requestFocus()
                    }
                }
            })

            editText.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (editText.text.isEmpty() && index > 0) {
                        otpFields[index - 1].requestFocus()
                        otpFields[index - 1].text.clear()
                    }
                }
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}