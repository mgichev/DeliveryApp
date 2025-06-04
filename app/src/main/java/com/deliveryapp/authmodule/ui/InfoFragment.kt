package com.deliveryapp.authmodule.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deliveryapp.deliverymodule.ui.MainActivity
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentAuthBinding
import com.example.deliveryapp.databinding.FragmentInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)

        binding.authBtn.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_emailCodeFragment)
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