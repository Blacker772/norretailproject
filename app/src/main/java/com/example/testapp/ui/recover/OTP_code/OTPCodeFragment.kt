package com.example.testapp.ui.recover.OTP_code

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentOtpCodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPCodeFragment : Fragment() {

    private var binding: FragmentOtpCodeBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOtpCodeBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btApply?.setOnClickListener {
            findNavController().navigate(R.id.action_OTPCodeFragment_to_passwordFragment)
        }
        binding?.btBack?.setOnClickListener {
            findNavController().popBackStack(R.id.mailFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}