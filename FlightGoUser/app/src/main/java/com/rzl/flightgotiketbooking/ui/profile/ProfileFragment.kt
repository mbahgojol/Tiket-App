package com.rzl.flightgotiketbooking.ui.profile

import android.Manifest
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.auth.FirebaseAuth
import com.rzl.flightgotiketbooking.data.model.ResponseProfile
import com.rzl.flightgotiketbooking.databinding.FragmentProfileBinding
import com.rzl.flightgotiketbooking.ui.PrivacynPolicyFragment
import com.rzl.flightgotiketbooking.ui.changepass.ChangePassFragment
import com.rzl.flightgotiketbooking.ui.editprofile.EditProfileFragment
import com.rzl.flightgotiketbooking.ui.help.HelpFragment
import com.rzl.flightgotiketbooking.ui.login.LoginActivity
import com.rzl.flightgotiketbooking.ui.payment.rememberPickImageGallery
import com.rzl.flightgotiketbooking.ui.tnc.TermsnConditionFragment
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

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfile()
        var profile: ResponseProfile? = null

        viewModel.resultState.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Success<*> -> {
                    profile = it.data as ResponseProfile
                    Glide.with(view).load(profile?.imageUser).into(binding.imgProfilePass)
                    binding.tvNameUser.text = profile?.role

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

        binding.btnDetailsAccount.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileFragment::class.java).apply {
                profile?.let {
                    putExtra("profile", it)
                }
            })
        }

        binding.btnChangePassword.setOnClickListener {
            startActivity(Intent(requireContext(), ChangePassFragment::class.java))
        }

        binding.btnTnc.setOnClickListener {
            startActivity(Intent(requireContext(), TermsnConditionFragment::class.java))
        }

        binding.btnHelp.setOnClickListener {
            startActivity(Intent(requireContext(), HelpFragment::class.java))
        }

        binding.btnPp.setOnClickListener {
            startActivity(Intent(requireContext(), PrivacynPolicyFragment::class.java))
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