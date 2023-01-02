package binar.academy.flightgoadmin.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.databinding.FragmentSplashScreenBinding
import binar.academy.flightgoadmin.ui.viewmodel.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var viewModel: AdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getToken().observe(viewLifecycleOwner) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (!it.isNullOrEmpty() && it != "undefined") {
                    Log.d("Token Available: ", it)
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_splashScreenFragment_to_homeFragment)
                    viewModel.getRole().observe(viewLifecycleOwner) {
                        Toast.makeText(context, "Halo, $it", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please Login First!", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_splashScreenFragment_to_loginFragment)
                }
            }, 2000L)

        }
    }
}