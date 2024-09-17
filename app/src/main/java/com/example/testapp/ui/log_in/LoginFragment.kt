package com.example.testapp.ui.log_in

import android.os.Bundle
import android.util.Log
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
import com.example.testapp.ui.recover.mail.MailFragment
import com.example.testapp.ui.sign_up.SignUpFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                onChangeStateLogIn(state)
            }
        }

        binding?.apply {
            btEnter.setOnClickListener {
                tiLogin.error = null
                tiPassword.error = null
                val login = etLoginText.text.toString().trim()
                val password = etPasswordText.text.toString().trim()
                val checkBox = cbSaveData

                if (login.isEmpty()) {
                    tiLogin.error = "Введите логин!"
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    tiPassword.error = "Введите пароль!"
                    return@setOnClickListener
                }

                lifecycleScope.launch {
                    try {
                        val result = viewModel.getUserLoginAuth(login)
                        if (checkBox.isChecked) {
                            handleSaveData(result, login, password)
                        } else {
                            viewModel.getLogin(AuthModel(login, password))
                        }
                    } catch (e: Exception) {
                        showToast(e.message.toString())
                    }
                }
            }

            onSetupFocusChange(etLoginText, tiLogin)
            onSetupFocusChange(etPasswordText, tiPassword)

            btSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
            btResetPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_mailFragment)
            }
        }
    }

    private fun handleSaveData(result: SaveUser?, login: String, password: String) {
        if (result != null) {
            if (result.password == password) {
                viewModel.getLogin(AuthModel(login, password))
            } else {
                showToast("Неверный логин и/или пароль")
            }
        } else {
            viewModel.saveUserAuth(SaveUser(null, login, password))
            viewModel.getLogin(AuthModel(login, password))
        }
    }

    //Метод, обрабатывающий состояния UiState
    private fun onChangeStateLogIn(state: UiStateLogIn) {
        binding?.apply {
            when (state) {
                is UiStateLogIn.Loading -> {
                    progressBar.isVisible = state.isLoading
                    setViewsEnabled(!state.isLoading)
                }

                is UiStateLogIn.Error -> {
                    Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                    progressBar.isVisible = false
                    setViewsEnabled(true)
                }

                is UiStateLogIn.Data -> {
                    progressBar.isVisible = false
                    findNavController().navigate(R.id.action_loginFragment_to_viewPagerFragment)
                }

                else -> {
                    progressBar.isVisible = false
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setViewsEnabled(isEnabled: Boolean) {
        binding?.apply {
            btEnter.isEnabled = isEnabled
            btSignUp.isEnabled = isEnabled
            btResetPassword.isEnabled = isEnabled
            cbSaveData.isEnabled = isEnabled
            tiLogin.isEnabled = isEnabled
            tiPassword.isEnabled = isEnabled
        }
    }

    private fun onSetupFocusChange(editText: EditText?, textInputLayout: TextInputLayout?) {
        editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textInputLayout?.error = null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}