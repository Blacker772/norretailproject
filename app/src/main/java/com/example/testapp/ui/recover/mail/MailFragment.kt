package com.example.testapp.ui.recover.mail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentMailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MailFragment : Fragment() {

    private var binding: FragmentMailBinding? = null
    private val viewModel by viewModels<MailViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner){}

        binding?.btSent?.setOnClickListener{
            val mail = binding?.etMail?.text.toString()
            if (mail.isNotEmpty()){
                lifecycleScope.launch {
                    viewModel.checkMail(mail)
                }
            }
            findNavController().navigate(R.id.action_recoverFragment_to_sentCodeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}