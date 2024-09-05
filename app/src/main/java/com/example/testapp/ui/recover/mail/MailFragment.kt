package com.example.testapp.ui.recover.mail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentMailBinding
import com.example.testapp.ui.log_in.UiStateLogIn
import com.example.testapp.ui.main_menu.viewpager.ViewPagerFragment
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

        lifecycleScope.launch {
            viewModel.state.collectLatest {
                onChangeState(it)
            }
        }

        binding?.btSent?.setOnClickListener{
            val mail = binding?.etMail?.text.toString()
            if (mail.isNotEmpty()){
                lifecycleScope.launch {
                    viewModel.checkMail(mail)
                }
            }else{
                binding?.tiMail?.error = "Введите почту!"
            }
        }
    }
    
    //Метод, обрабатывающий состояния UiState
    private fun onChangeState(state: UiStateMail) {
        when (state) {
            is UiStateMail.Loading -> {
                binding?.progressBar?.isVisible = state.isLoading
            }
            is UiStateMail.Error -> {
                Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                binding?.progressBar?.isVisible = state.isLoading
            }
            is UiStateMail.Data -> {
                binding?.progressBar?.isVisible = state.isLoading
                findNavController().navigate(R.id.action_recoverFragment_to_sentCodeFragment)
            }
            else -> {
                binding?.progressBar?.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}