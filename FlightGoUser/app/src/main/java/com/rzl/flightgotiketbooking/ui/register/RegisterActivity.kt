package com.rzl.flightgotiketbooking.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.rzl.flightgotiketbooking.databinding.ActivityRegisterBinding
import com.rzl.flightgotiketbooking.data.model.register.BodyAuth
import com.rzl.flightgotiketbooking.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.resultStateRegister.observe(this) {
            when (it) {
                is ResultState.Error -> {
                    Toast.makeText(this, it.e.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is ResultState.Loading -> {
                    binding.progressBar.isVisible = it.isloading
                }
                is ResultState.Success<*> -> {
                    finish()
                }
            }
        }

        binding.btnSignUp.setOnClickListener {
            val etNama = binding.etName.text.toString()
            val etEmail = binding.etEmail.text.toString()
            val etPwd = binding.etPassword.text.toString()

            val bodyRegister = BodyAuth(etEmail, etPwd)
            viewModel.register(bodyRegister)
        }

        binding.tvSignIn.setOnClickListener {
            finish()
        }
    }
}