package com.rzl.flightgotiketbooking.ui.editprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rzl.flightgotiketbooking.data.model.ResponseProfile
import com.rzl.flightgotiketbooking.databinding.FragmentEditProfileBinding

class EditProfileFragment : AppCompatActivity() {
    private lateinit var binding: FragmentEditProfileBinding

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
        }
    }
}