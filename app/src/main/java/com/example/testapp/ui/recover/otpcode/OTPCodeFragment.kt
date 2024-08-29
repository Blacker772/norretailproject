package com.example.testapp.ui.recover.otpcode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentSentCodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPCodeFragment : Fragment() {

    private var binding: FragmentSentCodeBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSentCodeBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btApply?.setOnClickListener {
            findNavController().navigate(R.id.action_sentCodeFragment_to_passwordFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}