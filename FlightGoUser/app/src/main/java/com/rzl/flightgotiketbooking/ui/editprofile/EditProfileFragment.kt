package com.rzl.flightgotiketbooking.ui.editprofile

import android.Manifest
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.bumptech.glide.Glide
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rzl.flightgotiketbooking.data.model.ResponseProfile
import com.rzl.flightgotiketbooking.databinding.FragmentEditProfileBinding
import com.rzl.flightgotiketbooking.ui.payment.rememberPickImageGallery
import com.rzl.flightgotiketbooking.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : AppCompatActivity() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("profile")) {
            val profile: ResponseProfile? = intent.getParcelableExtra("profile")
            binding.apply {
                Glide.with(this@EditProfileFragment).load(profile?.imageUser)
                    .into(binding.imgProfilePass)
                tvNameUser.text = profile?.role
                etEmail.setText(profile?.email.toString())
            }


            binding.composeView.addView(ComposeView(this).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    val permissionState =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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

            binding.imgProfilePass.setOnClickListener {
                viewModel.clickImg.value = true
            }

            viewModel.bitmap.observe(this) {
                if (it != null) {
                    Glide.with(this).load(it).into(binding.imgProfilePass)
                }
            }
        }
    }
}