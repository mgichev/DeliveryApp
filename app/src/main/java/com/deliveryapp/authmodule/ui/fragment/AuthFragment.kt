// Автор: Гичев М. А., КТбо4-8
// Тема: ВКР. Разработка мобильного приложения для работы курьера
// Описание: Основная страница входа


package com.deliveryapp.authmodule.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deliveryapp.authmodule.ui.viewmodel.AuthViewModel
import com.deliveryapp.deliverymodule.ui.MainActivity
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentAuthBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    val authViewModel: AuthViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.authBtn.setOnClickListener {
            val login = binding.loginTextField.editText?.text.toString()
            val password = binding.passwordTextField.editText?.text.toString()
            authViewModel.login(login, password)
        }

        authViewModel.successfulLogin.observe(viewLifecycleOwner) {
            if (it == true) {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        }

        authViewModel.unsuccessfulAuthAction.observe(viewLifecycleOwner) { it ->
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_AuthFragment_to_signInFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}