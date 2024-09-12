package com.example.testapp.ui.log_in

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
import com.example.testapp.data.auth.AuthModel
import com.example.testapp.data.database.entity.SaveUser
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.ui.menu.viewpager.ViewPagerFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
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

            btEnter.setOnClickListener {
                val login = etLoginText.text.toString().trim()
                val password = etPasswordText.text.toString().trim()
                val checkBox = cbSaveData

                if (login.isNotEmpty()) {
                    if (password.isNotEmpty()) {
                        if (checkBox.isChecked) {
                            lifecycleScope.launch {
                                val result = viewModel.getUserLoginAuth(login)
                                if (result != null) {
                                    if (result.password == password) {
                                        viewModel.getLogin(AuthModel(login, password))
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Неверный логин и/или пароль",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    viewModel.saveUserAuth(SaveUser(null, login, password))
                                    viewModel.getLogin(AuthModel(login, password))
                                }
                            }
                        } else {
                            viewModel.getLogin(AuthModel(login, password))
                        }
                    } else {
                        tiPassword.error = "Введите пароль!"
                    }
                } else {
                    tiLogin.error = "Введите логин!"
                }
            }

            onSetupFocusChange(etLoginText, tiLogin)
            onSetupFocusChange(etPasswordText, tiPassword)
            btSignUp.setOnClickListener {
                onNavigate(R.id.action_loginFragment_to_signUpFragment)
            }
            btResetPassword.setOnClickListener {
                onNavigate(R.id.action_loginFragment_to_recoverFragment)
            }
        }
    }

    //Метод, обрабатывающий состояния UiState
    private fun onChangeState(state: UiStateLogIn) {
        binding?.apply {
            when (state) {
                is UiStateLogIn.Loading -> {
                   progressBar.isVisible = state.isLoading
                    btEnter.isEnabled = !state.isLoading
                    btSignUp.isEnabled = !state.isLoading
                    btResetPassword.isEnabled = !state.isLoading
                    cbSaveData.isEnabled = !state.isLoading
                    tiLogin.isEnabled = !state.isLoading
                    tiPassword.isEnabled = !state.isLoading
                }

                is UiStateLogIn.Error -> {
                    Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                    progressBar.isVisible = true
                    btEnter.isEnabled = true
                    btSignUp.isEnabled = true
                    btResetPassword.isEnabled = true
                    cbSaveData.isEnabled = true
                    tiLogin.isEnabled = true
                    tiPassword.isEnabled = true
                }

                is UiStateLogIn.Data -> {
                    progressBar.isVisible = state.isLoading
                    onAction(ViewPagerFragment())
                }

                else -> {
                    progressBar.isVisible = false
                }
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

    private fun onNavigate(id: Int) {
        findNavController().navigate(id)
    }

    private fun onAction(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}