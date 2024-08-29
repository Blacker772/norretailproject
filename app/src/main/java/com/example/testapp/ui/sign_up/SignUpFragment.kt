package com.example.testapp.ui.sign_up

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentSignUpBinding
import com.example.testapp.ui.log_in.LoginFragment
import com.example.testapp.ui.main_menu.viewpager.ViewPagerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var binding: FragmentSignUpBinding? = null
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btBack?.setOnClickListener {
            findNavController().popBackStack()
        }

        binding?.btRegisterUser?.setOnClickListener {
            val login = binding?.etLogin?.text.toString()
            val password = binding?.etPassword?.text.toString()
            val checkPassword = binding?.etPassword2?.text.toString()
            val family = binding?.etFamily?.text.toString()
            val name = binding?.etName?.text.toString()
            val lastname = binding?.etLastname?.text.toString()
            val email = binding?.etMail?.text.toString()

            if (login.isNotEmpty()
                && password.isNotEmpty()
                && checkPassword.isNotEmpty()
                && family.isNotEmpty()
                && name.isNotEmpty()
                && lastname.isNotEmpty()
                && email.isNotEmpty()
            ) {
                if (password.length >= 6) {
                    binding?.tiPassword?.error = null
                    if (password == checkPassword) {
                        binding?.tiPassword2?.error = null
                        if (email.isEmailValid()) {
                            binding?.tiMail?.error = null
                            lifecycleScope.launch {
                                viewModel.createUser(
                                    login = login,
                                    password = password,
                                    family = family,
                                    name = name,
                                    lastname = lastname,
                                    email = email
                                )
                                viewModel.state.collect{
                                    onChangeState(it)
                                }
                            }
                        } else {
                            binding?.tiMail?.error = "Неправильный формат почты!"
                        }
                    } else {
                        binding?.tiPassword2?.error = "Пароли не совпадают!"
                    }
                } else {
                    binding?.tiPassword?.error = "Пароль должен содержать минимум 6 символов!"
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Пожалуйста заполните все поля!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val etLogin = binding?.etLogin
        etLogin?.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                binding?.etPassword?.requestFocus()
                true
            } else {
                false
            }
        }

        val etPassword = binding?.etPassword
        etPassword?.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding?.tiPassword?.error = null
                }
            }

            setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                    binding?.etPassword2?.requestFocus()
                    true
                } else {
                    false
                }
            }
        }

        val etPassword2 = binding?.etPassword2
        etPassword2?.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding?.tiPassword2?.error = null
                }
            }
            setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                    binding?.etMail?.requestFocus()
                    true
                } else {
                    false
                }
            }
        }

        val etMail = binding?.etMail
        etMail?.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding?.tiMail?.error = null
                }
            }
            setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                    binding?.etFamily?.requestFocus()
                    true
                } else {
                    false
                }
            }
        }

        val etFamily = binding?.etFamily
        etFamily?.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                binding?.etName?.requestFocus()
                true
            } else {
                false
            }
        }

        val etName = binding?.etName
        etName?.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                binding?.etLastname?.requestFocus()
                true
            } else {
                false
            }
        }

        val etLastname = binding?.etLastname
        etLastname?.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                val action = getSystemService(requireContext(), InputMethodManager::class.java)
                action?.hideSoftInputFromWindow(etLastname.windowToken, 0)
                true
            } else {
                false
            }
        }
    }

    private fun onChangeState(state: UiStateSignUp) {
        when (state) {
            is UiStateSignUp.Loading -> {
                binding?.progressBar?.isVisible = state.isLoading
            }

            is UiStateSignUp.Error -> {
                Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                binding?.progressBar?.isVisible = state.isLoading
            }

            is UiStateSignUp.Data -> {
                binding?.progressBar?.isVisible = state.isLoading
                action(LoginFragment())
            }

            else -> {}
        }
    }
    private fun action(fragment: Fragment){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

