package binar.academy.flightgoadmin.ui.addtiket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import binar.academy.flightgoadmin.databinding.FragmentAddTiketBinding
import binar.academy.flightgoadmin.ui.viewmodel.AdminViewModel


class AddTiketFragment : Fragment() {

    private var _binding: FragmentAddTiketBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddTiketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            // One Way
            val oneType = binding.JsPenerbangan.checkedRadioButtonId
            val oneForm = binding.BentukPenerbangan.checkedRadioButtonId
            val oneDepCity = binding.edKotaAsal.text.toString()
            val oneArrCity = binding.edKotaTujuan.text.toString()
            val oneDepPort = binding.edBandaraAsal.text.toString()
            val oneArrPort = binding.edBandaraTujuan.text.toString()
            val oneDepDate = binding.edDepartDate.text.toString()
            val oneDepPrice = binding.edPrice.text.toString()
            val oneDepImg = binding.edImage.text.toString()
            val oneDepDesc = binding.edDesc.text.toString()

            // Round Trip
            val roundDepCity = binding.edKodeAsalReturn.text.toString()
            val roundArrCity = binding.edKotaTujuanReturn.text.toString()
            val roundDepPort = binding.edBandaraAsalReturn.text.toString()
            val roundArrPort = binding.edBandaraTujuanReturn.text.toString()
            val roundDepDate = binding.edDepartDateReturn.text.toString()
            val roundDepPrice = binding.edPriceReturn.text.toString()
            val roundDepImg = binding.edImageReturn.text.toString()
            val roundDepDesc = binding.edDescReturn.text.toString()

            val viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        }
    }


}