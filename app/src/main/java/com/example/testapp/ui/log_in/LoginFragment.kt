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
import androidx.room.RoomDatabase
import com.example.testapp.R
import com.example.testapp.data.database.AppDatabase
import com.example.testapp.data.database.entity.SaveUser
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.ui.main_menu.viewpager.ViewPagerFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
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
                val checkBox = binding?.cbSaveData

                if (login.isNotEmpty()) {
                    if (password.isNotEmpty()) {
                        lifecycleScope.launch {
                            viewModel.getLogin(login, password)
                        }
                        if(checkBox?.isChecked == true){
                            Log.d("databaseAAA", "onViewCreated: saved user ")
                            viewModel.saveUser(SaveUser(null, login, password))
                        }
                    } else {
                        binding?.tiPassword?.error = "Введите пароль!"
                    }
                } else {
                    binding?.tiLogin?.error = "Введите логин!"
                }
            }
            setupFocusChange(etLoginText, tiLogin)
            setupFocusChange(etPasswordText, tiPassword)
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

    private fun setupFocusChange(editText: EditText?, textInputLayout: TextInputLayout?) {
        editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textInputLayout?.error = null
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