package com.rzl.flightgotiketbooking.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.databinding.ActivityLoginBinding
import com.rzl.flightgotiketbooking.data.model.register.BodyAuth
import com.rzl.flightgotiketbooking.ui.MainActivity
import com.rzl.flightgotiketbooking.ui.register.RegisterActivity
import com.rzl.flightgotiketbooking.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //btn
        binding.btnSignIn.setOnClickListener {
            val etEmail = binding.etEmail.text.toString()
            val etPwd = binding.etPassword.text.toString()

            viewModel.login(BodyAuth(etEmail, etPwd))
        }

        viewModel.resultLogin.observe(this) {
            when (it) {
                is ResultState.Loading -> {
                    binding.progressBar.isVisible = it.isloading
                }
                is ResultState.Error -> {
                    Toast.makeText(this, it.e.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is ResultState.Success<*> -> {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            }
        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }


        //sign google
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnGoogle.setOnClickListener {
            signInGoole()
        }
    }

    private fun signInGoole() {
        val signInItent = googleSignInClient.signInIntent
        launcher.launch(signInItent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResult(task)
            }
        }


    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent: Intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name", account.displayName)
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}