package binar.academy.flightgoadmin.ui.profile

import android.Manifest
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
import androidx.navigation.fragment.findNavController
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.data.model.admin.ResponseProfile
import binar.academy.flightgoadmin.databinding.FragmentProfileBinding
import binar.academy.flightgoadmin.ui.component.rememberPickImageGallery
import binar.academy.flightgoadmin.utils.ResultState
import com.bumptech.glide.Glide
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
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

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfile()

        binding.view.addView(ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    rememberMultiplePermissionsState(
                        permissions = listOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_MEDIA_IMAGES,
                        )
                    )
                } else {
                    rememberMultiplePermissionsState(
                        permissions = listOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        )
                    )
                }

                LaunchedEffect(Unit) {
                    permissionState.launchMultiplePermissionRequest()
                }

                PermissionsRequired(multiplePermissionsState = permissionState,
                    permissionsNotGrantedContent = { },
                    permissionsNotAvailableContent = { }) {}

                val pickImgGallery = rememberPickImageGallery()
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    viewModel.clickImg.value = false
                    pickImgGallery.imageUri = uri
                    pickImgGallery.imageUri?.let {
                        pickImgGallery.bitmap = if (Build.VERSION.SDK_INT < 28) {
                            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                        } else {
                            val source = ImageDecoder.createSource(context.contentResolver, it)
                            ImageDecoder.decodeBitmap(source)
                        }
                        viewModel.bitmap.value = it
                    }
                }

                val onclick by viewModel.clickImg.collectAsState()

                LaunchedEffect(onclick) {
                    onclick.let {
                        if (it) {
                            launcher.launch("image/*")
                        }
                    }
                }
            }
        })

        binding.imgProfile.setOnClickListener {
            viewModel.clickImg.value = true
        }

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

        viewModel.bitmap.observe(viewLifecycleOwner) {
            if (it != null) {
                Glide.with(view).load(it).into(binding.imgProfile)
            }
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
}