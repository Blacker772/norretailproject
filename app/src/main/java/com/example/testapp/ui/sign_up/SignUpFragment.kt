package com.example.testapp.ui.sign_up

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testapp.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btRegisterUser.setOnClickListener {
            val login = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()
            val checkPassword = binding.etPassword2.text.toString()
            val family = binding.etFamily.text.toString()
            val name = binding.etName.text.toString()
            val lastname = binding.etLastname.text.toString()
            val email = binding.etMail.text.toString()

            if (login.isNotEmpty()
                && password.isNotEmpty()
                && checkPassword.isNotEmpty()
                && family.isNotEmpty()
                && name.isNotEmpty()
                && lastname.isNotEmpty()
                && email.isNotEmpty()
            ) {
                if (password.length >= 6) {
                    if (password == checkPassword) {

                        if (email.isEmailValid()) {
                            lifecycleScope.launch {
                                viewModel.createUser(
                                    login = login,
                                    password = password,
                                    family = family,
                                    name = name,
                                    lastname = lastname,
                                    email = email
                                )
                            }
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(requireContext(), "Аккаунт успешно создан!", Toast.LENGTH_SHORT).show()
                            }
                            findNavController().popBackStack()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Неправильный формат почты!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(requireContext(), "Пароли не совпадают!", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Пароль должен содержать минимум 6 символов!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    "Пожалуйста заполните все поля!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }
}

