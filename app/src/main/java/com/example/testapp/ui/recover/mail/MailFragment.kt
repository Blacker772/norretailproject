package com.example.testapp.ui.recover.mail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentMailBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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

//        lifecycleScope.launch {
//            viewModel.state.collectLatest { state ->
////                onChangeStateMail(state)
//            }
//        }

        binding?.apply {
            btSent.setOnClickListener {
//                val mail = etMail.text.toString().trim()
//                if (mail.isNotEmpty()){
//                    lifecycleScope.launch {
//                        viewModel.checkMail(mail)
//                    }
//                }else{
//                    tiMail.error = "Введите почту!"
//                }
//
//            }
//            onSetupFocusChange(etMail, tiMail)
                findNavController().navigate(R.id.action_mailFragment_to_OTPCodeFragment)
            }
            btBack.setOnClickListener {
                findNavController().navigate(R.id.action_mailFragment_to_loginFragment)
            }
        }
    }

    private fun onSetupFocusChange(editText: EditText?, textInputLayout: TextInputLayout?) {
        editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textInputLayout?.error = null
            }
        }
    }

    //Метод, обрабатывающий состояния UiState
    private fun onChangeStateMail(state: UiStateMail) {
        binding?.apply {
            when (state) {
                is UiStateMail.Loading -> {
                    progressBar.isVisible = state.isLoading
                }

                is UiStateMail.Error -> {
                    progressBar.isVisible = false
                    Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                }

                is UiStateMail.Data -> {
                    progressBar.isVisible = false
                    findNavController().navigate(R.id.action_mailFragment_to_OTPCodeFragment)
                }

                else -> {
                    progressBar.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}