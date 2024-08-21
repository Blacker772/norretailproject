package com.example.testapp.ui.log_in

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
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.ui.UiState
import com.example.testapp.ui.main_menu.ViewPagerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btSignUp.setOnClickListener {
                navigate(R.id.action_loginFragment_to_signUpFragment)
            }
            btResetPassword.setOnClickListener {
                navigate(R.id.action_loginFragment_to_recoverFragment)
            }

            btEnter.setOnClickListener {
                val login = binding.etLoginText.text.toString()
                val password = binding.etPasswordText.text.toString()

                if (login.isNotEmpty() && password.isNotEmpty()) {
                    lifecycleScope.launch {
                        viewModel.getLogin(login, password)
                        viewModel.state.collect {
                            onChangeState(it)
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Введите данные!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //Метод, обрабатывающий состояния UiState
    private fun onChangeState(state: UiState) {
        when (state) {
            is UiState.Loading -> {
                binding.progressBar.isVisible = state.isLoading
            }

            is UiState.Error -> {
                Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                binding.progressBar.isVisible = state.isLoading
            }

            is UiState.Data -> {
                binding.progressBar.isVisible = state.isLoading
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ViewPagerFragment())
                    .commit()
            }

            else -> {}
        }
    }

    private fun navigate(id: Int) {
        findNavController().navigate(id)
    }
}