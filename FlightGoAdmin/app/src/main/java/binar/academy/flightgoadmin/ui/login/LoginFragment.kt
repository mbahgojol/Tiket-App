package binar.academy.flightgoadmin.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.databinding.FragmentLoginBinding
import binar.academy.flightgoadmin.ui.viewmodel.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignIn.setOnClickListener {
            loginAction()
        }
    }

    private fun loginAction() {
        val email = binding.adminEmail.text.toString()
        val password = binding.adminPassword.text.toString()
        if (email == "" && password == "") {
            binding.edEmail.error = "Please fill out this field."
            binding.edPassword.error = "Please fill out this field."
        } else {
            viewModel.saveLoginStatus(true)
            viewModel.apiLogin(email, password)
            viewModel.LoginLive().observe(viewLifecycleOwner) {
                if (it != null) {
                    //save token to Data Store
                    viewModel.saveData(it.data.role, it.data.accessToken)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    Log.d("ACCESS TOKEN: ", it.data.accessToken)
                    Toast.makeText(context, "Halo ${it.data.role}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed Login", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}