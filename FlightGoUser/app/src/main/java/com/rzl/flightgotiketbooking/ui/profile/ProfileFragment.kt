package com.rzl.flightgotiketbooking.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.rzl.flightgotiketbooking.data.model.ResponseProfile
import com.rzl.flightgotiketbooking.databinding.FragmentProfileBinding
import com.rzl.flightgotiketbooking.ui.login.LoginActivity
import com.rzl.flightgotiketbooking.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfile()

        viewModel.resultState.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Success<*> -> {
                    val response = it.data as ResponseProfile
                    Glide.with(view)
                        .load(response.imageUser)
                        .into(binding.imgProfile)

                    binding.tvNameUser.text = response.email

                }
                is ResultState.Error -> {
                    Toast.makeText(requireContext(), it.e.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is ResultState.Loading -> {
                    binding.progresBar.isVisible = it.isloading
                }
            }
        }

        auth = FirebaseAuth.getInstance()
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            viewModel.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}