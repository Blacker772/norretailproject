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
import com.example.testapp.data.createuser.CreateUserModel
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.ui.main_menu.viewpager.ViewPagerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.state.collect {
                onChangeState(it)
            }
        }

        binding?.apply {
            btSignUp.setOnClickListener {
                navigate(R.id.action_loginFragment_to_signUpFragment)
            }
            btResetPassword.setOnClickListener {
                navigate(R.id.action_loginFragment_to_recoverFragment)
            }

            btEnter.setOnClickListener {
                val login = binding?.etLoginText?.text.toString()
                val password = binding?.etPasswordText?.text.toString()

                if (login.isNotEmpty()) {
                    if (password.isNotEmpty()) {
                        lifecycleScope.launch {
                            viewModel.getLogin(login, password)
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(requireContext(), "Ваш аккаунт успешно создан", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        binding?.tiPassword?.error = "Введите пароль!"
                    }
                } else {
                    binding?.tiLogin?.error = "Введите логин!"
                }
            }
        }

        binding?.apply {
            etLoginText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding?.tiLogin?.error = null
                }
            }
            etPasswordText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding?.tiPassword?.error = null
                }
            }
        }
    }

    //Метод, обрабатывающий состояния UiState
    private fun onChangeState(state: UiStateLogIn) {
        when (state) {
            is UiStateLogIn.Loading -> {
                binding?.progressBar?.isVisible = state.isLoading
            }

            is UiStateLogIn.Error -> {
                Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                binding?.progressBar?.isVisible = state.isLoading
            }

            is UiStateLogIn.Data -> {
                binding?.progressBar?.isVisible = state.isLoading
                action(ViewPagerFragment())
            }

            else -> {
                binding?.progressBar?.isVisible = false
            }
        }
    }

    private fun navigate(id: Int) {
        findNavController().navigate(id)
    }

    private fun action(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}