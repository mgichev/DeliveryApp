package com.deliveryapp.deliverymodule.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deliveryapp.deliverymodule.domain.model.PersonalInfo
import com.example.deliveryapp.databinding.FragmentUserDataBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDataFragment : Fragment() {

    private var _binding: FragmentUserDataBinding? = null
    private val binding get() = _binding!!
    val accountViewModel: AccountViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDataBinding.inflate(inflater, container, false)

        binding.btn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.topBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.saveBtn.setOnClickListener {
            val info = PersonalInfo(
                fio = binding.editFullName.text.toString(),
                phoneNumber = binding.editPhoneNumber.text.toString(),
                address = binding.editAddress.text.toString(),
                passport = binding.editPassport.text.toString(),
                inn = binding.editInn.text.toString()
            )
            accountViewModel.updatePersonalInfo(info)
        }

        accountViewModel.personalInfo.observe(viewLifecycleOwner) { info ->
            if (info != null) {
                binding.editFullName.setText(info.fio)
                binding.editPhoneNumber.setText(info.phoneNumber)
                binding.editAddress.setText(info.address)
                binding.editPassport.setText(info.passport)
                binding.editInn.setText(info.inn)
            }
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}