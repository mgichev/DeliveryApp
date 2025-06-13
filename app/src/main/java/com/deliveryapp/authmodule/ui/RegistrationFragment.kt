package com.deliveryapp.authmodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deliveryapp.deliverymodule.domain.User
import com.deliveryapp.deliverymodule.domain.model.PersonalInfo
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentSigninBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!
    val authViewModel: AuthViewModel by activityViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.authBtn.setOnClickListener {
            val email = binding.emailTextField.editText?.text.toString()
            val password1 = binding.passwordFirstTextField.editText?.text.toString()
            val password2 = binding.passwordSecondTextField.editText?.text.toString()


            if (password1 == password2)
                authViewModel.createNewUser(email, password1)


        }

        authViewModel.successfulRegistration.observe(viewLifecycleOwner) {

            val phoneNumber = binding.loginTextField.editText?.text.toString()

            authViewModel.setUser(
                User(
                    FirebaseAuth.getInstance().currentUser?.uid ?: "test",
                    email = binding.emailTextField.editText?.text.toString(),
                    personalInfo = PersonalInfo("", phoneNumber = phoneNumber, "", "", "")
                )
            )
            findNavController().navigate(R.id.action_signInFragment_to_infoFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}