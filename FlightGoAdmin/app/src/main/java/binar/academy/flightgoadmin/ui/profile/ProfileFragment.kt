package binar.academy.flightgoadmin.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.data.model.admin.ResponseProfile
import binar.academy.flightgoadmin.databinding.FragmentProfileBinding
import binar.academy.flightgoadmin.utils.ResultState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {
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
                    Glide.with(view).load(response.imageUser).into(binding.imgProfile)

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

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
}