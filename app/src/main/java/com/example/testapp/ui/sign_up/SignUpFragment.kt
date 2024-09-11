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
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentSignUpBinding
import com.example.testapp.ui.log_in.LoginFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var binding: FragmentSignUpBinding? = null
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Установка слушателей состояния UiState
        lifecycleScope.launch {
            viewModel.state.collect {
                onChangeState(it)
            }
        }

        binding?.apply {

            //Установка слушателей нажатия клавиш
            setupEditorAction(etLogin, etPassword)
            setupEditorAction(etPassword, etPasswordCheck)
            setupEditorAction(etPasswordCheck, etMail)
            setupEditorAction(etMail, etFamily)
            setupEditorAction(etFamily, etName)
            setupEditorAction(etName, etLastname)

            //Установка слушателей нажатия клавиш
            setupCloseKeyboard(etLastname)

            //Установка слушателей нажатия фокуса
            setupFocusChange(etPassword, tiPassword)
            setupFocusChange(etPasswordCheck, tiPassword2)
            setupFocusChange(etMail, tiMail)

            //Установка слушателей нажатия кнопок
            btRegisterUser.setOnClickListener {
                val login = etLogin.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val checkPassword = etPasswordCheck.text.toString().trim()
                val family = etFamily.text.toString().trim()
                val name = etName.text.toString().trim()
                val lastname = etLastname.text.toString().trim()
                val email = etMail.text.toString().trim()

                fun onSubmit() {
                    if (validateInputs(
                            login, password,
                            checkPassword, email,
                            family, name, lastname
                        )
                    ) {
                        lifecycleScope.launch {
                            viewModel.createUser(login, password, family, name, lastname, email)
                        }
                    }
                }
                onSubmit()
            }

            btBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    //Метод для установки слушателей нажатия клавиш
    private fun setupEditorAction(
        editText: EditText?, nextFocusView: View?,
        actionIdToHandle: Int = EditorInfo.IME_ACTION_NEXT,
    ) {
        editText?.setOnEditorActionListener { _, actionId, event ->
            if (actionId == actionIdToHandle || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                nextFocusView?.requestFocus()
                true
            } else {
                false
            }
        }
    }

    //Метод для установки слушателей нажатия фокуса
    private fun setupFocusChange(editText: EditText?, textInputLayout: TextInputLayout?) {
        editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textInputLayout?.error = null
            }
        }
    }

    //Метод для закрытия клавиатуры
    private fun setupCloseKeyboard(
        editText: EditText?,
        actionIdToHandle: Int = EditorInfo.IME_ACTION_DONE,
    ) {
        editText?.setOnEditorActionListener { _, actionId, event ->
            if (actionId == actionIdToHandle || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                val imm = getSystemService(requireContext(), InputMethodManager::class.java)
                imm?.hideSoftInputFromWindow(editText.windowToken, 0)
                true
            } else {
                false
            }
        }
    }

    //Метод для валидации введенных данных
    private fun validateInputs(
        login: String, password: String,
        checkPassword: String, email: String,
        family: String, name: String, lastname: String,
    ): Boolean {
        return when {
            login.isEmpty() -> showToast("Логин не может быть пустым")
            password.isEmpty() -> showToast("Пароль не может быть пустым")
            checkPassword.isEmpty() -> showToast("Повторите пароль")
            email.isEmpty() -> showToast("Почта не может быть пустой")
            family.isEmpty() -> showToast("Фамилия не может быть пустой")
            name.isEmpty() -> showToast("Имя не может быть пустым")
            lastname.isEmpty() -> showToast("Отчество не может быть пустым")
            password.length < 6 -> {
                binding?.tiPassword?.error = "Пароль должен содержать минимум 6 символов!"
                false
            }

            password != checkPassword -> {
                binding?.tiPassword2?.error = "Пароли не совпадают!"
                false
            }

            !email.isEmailValid() -> {
                binding?.tiMail?.error = "Неправильный формат почты!"
                false
            }

            else -> true
        }
    }

    //Метод для показа Toast
    private fun showToast(message: String): Boolean {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        return false
    }

    //Метод, обрабатывающий состояния UiState
    private fun onChangeState(state: UiStateSignUp) {
        binding?.apply {
            when (state) {
                is UiStateSignUp.Loading -> {
                    progressBar.isVisible = state.isLoading
                }

                is UiStateSignUp.Error -> {
                    progressBar.isVisible = false
                    Toast.makeText(requireContext(), "${state.message}", Toast.LENGTH_SHORT).show()
                }

                is UiStateSignUp.Data -> {
                    progressBar.isVisible = false
                    action(LoginFragment())
                }

                else -> {
                    progressBar.isVisible = false
                }
            }
        }

    }

    //Метод для переключения фрагментов
    private fun action(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    //Метод для проверки формата почты
    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}