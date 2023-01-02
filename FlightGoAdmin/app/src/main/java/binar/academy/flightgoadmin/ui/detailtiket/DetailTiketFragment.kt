package binar.academy.flightgoadmin.ui.detailtiket

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.data.model.tiket.TiketResponseItem
import binar.academy.flightgoadmin.databinding.FragmentDetailTiketBinding
import binar.academy.flightgoadmin.ui.adapter.DateTimePIcker.datePick
import binar.academy.flightgoadmin.ui.adapter.DateTimePIcker.timePick
import binar.academy.flightgoadmin.ui.adapter.UriToFile
import binar.academy.flightgoadmin.ui.viewmodel.AdminViewModel
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*

@AndroidEntryPoint
class DetailTiketFragment : Fragment() {

    private var _binding: FragmentDetailTiketBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AdminViewModel

    companion object {
        // Permission request constant for External storage access
        const val REQUEST_CODE_PERMISSIONS = 101

        // Bundle Constant to save the count of permission requests retried
        const val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"

        // Constant to limit the number of permission request retries
        const val MAX_NUMBER_REQUEST_PERMISSIONS = 2
    }

    // List of permissions required by the app to access external storage
    // to allow the user to select an image
    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    //image upload
    private var getFile: File? = null
    private val imagePic =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val img = UriToFile(requireContext()).getImageBody(uri)
                getFile = img
                binding.imageProd.setImageURI(uri)
            }
        }

    // Stores the count of permission requests retried
    private var permissionRequestCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailTiketBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]

        // Make sure the app has correct permissions to run
        requestPermissionsIfNecessary()

        // When activity is reloaded after configuration change
        savedInstanceState?.let {
            // Restore the permission request count
            permissionRequestCount = it.getInt(KEY_PERMISSIONS_REQUEST_COUNT, 0)
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get data from home adapter
        val getBun = arguments?.getSerializable("dataTiket") as TiketResponseItem

        val id_tiket = getBun.id.toString()
        val price = getBun.price.toString()
        val totalPrice = getBun.totalPrice.toString()
        val price_ = getBun.price_return.toString()

        if (getBun.bentukPenerbangan == "round-trip") {
            binding.apply {
                cardReturn.visibility = View.VISIBLE
            }
        }

        //binding set text
        when (getBun.jenisPenerbangan) {
            "Internasional" -> binding.JsPenerbangan.check(R.id.Internasional)
            "Domestik" -> binding.JsPenerbangan.check(R.id.Domestik)
        }
        when (getBun.bentukPenerbangan) {
            "Roundtrip" -> binding.BentukPenerbangan.check(R.id.Roundtrip)
            "Oneway" -> binding.BentukPenerbangan.check(R.id.Oneway)
        }

        binding.imageId.visibility = View.VISIBLE
        binding.apply {
            //card Departure
            Glide.with(requireContext()).load(getBun.imageProduct).timeout(6000).into(imageProd)
            imageId.text = getBun.imageProductId
            edBandaraAsal.setText(getBun.bandaraAsal)
            edBandaraTujuan.setText(getBun.bandaraTujuan)
            edDepartDate.setText(getBun.depatureDate)
            edDepartTime.setText(getBun.depatureTime)
            edKodeAsal.setText(getBun.kodeNegaraAsal)
            edKodeTujuan.setText(getBun.kodeNegaraTujuan)
            edKotaAsal.setText(getBun.kotaAsal)
            edKotaTujuan.setText(getBun.kotaTujuan)
            edPrice.setText(price)
            //card return
            edBandaraAsalReturn.setText(getBun.bandaraAsal_return)
            edBandaraTujuanReturn.setText(getBun.bandaraTujuan_return)
            edDepartDateReturn.setText(getBun.depatureDate_return)
            edDepartTimeReturn.setText(getBun.depatureTime_return)
            edKodeAsalReturn.setText(getBun.kodeNegaraAsal_return)
            edKodeTujuanReturn.setText(getBun.kodeNegaraTujuan_return)
            edKotaAsalReturn.setText(getBun.kotaAsal_return)
            edKotaTujuanReturn.setText(getBun.kotaTujuan_return)
            edPriceReturn.setText(price_)
            //total price
            edTotalPrice.setText(totalPrice)
            edDesc.setText(getBun.desctiption)
        }

        //update tiket
        binding.btnUpdate.setOnClickListener {
            var penerbangan = binding.BentukPenerbangan.checkedRadioButtonId
            val bentuk_penerbangan = resources.getResourceEntryName(penerbangan)
            val rute = binding.JsPenerbangan.checkedRadioButtonId
            val jenis_penerbangan = resources.getResourceEntryName(rute)
            val ban_asal = binding.edBandaraAsal.text.toString()
            val ban_tujuan = binding.edBandaraTujuan.text.toString()
            val depart_date = binding.edDepartDate.text.toString()
            val depart_time = binding.edDepartTime.text.toString()
            val kode_asal = binding.edKodeAsal.text.toString()
            val kode_tujuan = binding.edKodeTujuan.text.toString()
            val kota_asal = binding.edKotaAsal.text.toString()
            val kota_tujuan = binding.edKotaTujuan.text.toString()
            val price_depart = binding.edPrice.text.toString()
            val ban_asal_ = binding.edBandaraAsalReturn.text.toString()
            val ban_tujuan_ = binding.edBandaraTujuanReturn.text.toString()
            val depart_date_ = binding.edDepartDateReturn.text.toString()
            val depart_time_ = binding.edDepartTimeReturn.text.toString()
            val kode_asal_ = binding.edKodeAsalReturn.text.toString()
            val kode_tujuan_ = binding.edKodeTujuanReturn.text.toString()
            val kota_asal_ = binding.edKotaAsalReturn.text.toString()
            val kota_tujuan_ = binding.edKotaTujuanReturn.text.toString()
            val price_return = binding.edPriceReturn.text.toString()
            val total_ = binding.edTotalPrice.text.toString()
            val desc = binding.edDesc.text.toString()

            if (getFile != null) {
                val requestFile = getFile!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "image_product", getFile!!.name, requestFile
                )

                viewModel.getToken().observe(viewLifecycleOwner) {
                    if (it != null) {
                        val token = "Bearer $it"
                        Log.d("TOKEN", it)
                        viewModel.putTiketById(
                            token,
                            id_tiket,
                            ban_asal.toRequestBody("text/plain".toMediaTypeOrNull()),
                            ban_asal_.toRequestBody("text/plain".toMediaTypeOrNull()),
                            ban_tujuan.toRequestBody("text/plain".toMediaTypeOrNull()),
                            ban_tujuan_.toRequestBody("text/plain".toMediaTypeOrNull()),
                            bentuk_penerbangan.toRequestBody("text/plain".toMediaTypeOrNull()),
                            depart_date.toRequestBody("text/plain".toMediaTypeOrNull()),
                            depart_date_.toRequestBody("text/plain".toMediaTypeOrNull()),
                            depart_time.toRequestBody("text/plain".toMediaTypeOrNull()),
                            depart_time_.toRequestBody("text/plain".toMediaTypeOrNull()),
                            desc.toRequestBody("text/plain".toMediaTypeOrNull()),
                            imageMultipart,
                            jenis_penerbangan.toRequestBody("text/plain".toMediaTypeOrNull()),
                            kode_asal.toRequestBody("text/plain".toMediaTypeOrNull()),
                            kode_asal_.toRequestBody("text/plain".toMediaTypeOrNull()),
                            kode_tujuan.toRequestBody("text/plain".toMediaTypeOrNull()),
                            kode_tujuan_.toRequestBody("text/plain".toMediaTypeOrNull()),
                            kota_asal.toRequestBody("text/plain".toMediaTypeOrNull()),
                            kota_asal_.toRequestBody("text/plain".toMediaTypeOrNull()),
                            kota_tujuan.toRequestBody("text/plain".toMediaTypeOrNull()),
                            kota_tujuan_.toRequestBody("text/plain".toMediaTypeOrNull()),
                            price_depart.toRequestBody("text/plain".toMediaTypeOrNull()),
                            price_return.toRequestBody("text/plain".toMediaTypeOrNull()),
                            total_.toRequestBody("text/plain".toMediaTypeOrNull())
                        )
                        Toast.makeText(context, "Berhasil Update", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_detailTiketFragment_to_homeFragment)
                    } else {
                        Toast.makeText(context, "Gagal Update", Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

        //Select Date
        binding.edDepartDate.setOnClickListener {
            datePick(requireActivity().supportFragmentManager, binding.edDepartDate)
        }
        binding.edDepartDateReturn.setOnClickListener {
            datePick(requireActivity().supportFragmentManager, binding.edDepartDateReturn)
        }
        //Select Time
        binding.edDepartTime.setOnClickListener {
            timePick(requireActivity().supportFragmentManager, binding.edDepartTime)
        }
        binding.edDepartTimeReturn.setOnClickListener {
            timePick(requireActivity().supportFragmentManager, binding.edDepartTimeReturn)
        }
        //Select Image
        binding.imageProd.setOnClickListener {
            requireActivity().intent.type = "image/*"
            imagePic.launch("image/*")
        }

        //DELETE TIKET
        binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext()).setTitle("Hapus Tiket")
                .setMessage("Yakin untuk menghapus item?").setCancelable(false)
                .setNegativeButton("Cancel") { dialog, _ ->
                    // Respond to negative button press
                    dialog.cancel()
                }.setPositiveButton("Hapus") { _, _ ->
                    // Respond to positive button press
                    viewModel.getToken().observe(viewLifecycleOwner) {
                        if (it != null) {
                            viewModel.deleteTiket("Bearer $it", id_tiket)
                            Toast.makeText(context, "Berhasil menghapus item", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(R.id.action_detailTiketFragment_to_homeFragment)
                        } else {
                            Toast.makeText(context, "Gagal menghapus item", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }.show()
        }


    }

    //Request Permissions
    private fun requestPermissionsIfNecessary() {
        // Check if all required permissions are granted
        if (!checkAllPermissions()) {
            // When all required permissions are not granted yet

            if (permissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                // When the number of permission request retried is less than the max limit set
                permissionRequestCount += 1 // Increment the number of permission requests done
                // Request the required permissions for external storage access
                ActivityCompat.requestPermissions(
                    requireActivity(), permissions, REQUEST_CODE_PERMISSIONS
                )
            } else {
                // Disable the "Select Image" button when access is denied by the user
                binding.imageProd.isEnabled = false
                // When the number of permission request retried exceeds the max limit set
                // Show a toast about how to update the permission for storage access
                Toast.makeText(
                    context,
                    "Go to Settings -> Apps and Notifications -> Flight Go Admin -> App Permissions and grant access to Storage.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkAllPermissions(): Boolean {
        // Boolean state to indicate all permissions are granted
        var hasPermissions = true
        // Verify all permissions are granted
        for (permission in permissions) {
            hasPermissions = hasPermissions && isPermissionGranted(permission)
        }
        // Return the state of all permissions granted
        return hasPermissions
    }

    private fun isPermissionGranted(permission: String) = ContextCompat.checkSelfPermission(
        requireContext(), permission
    ) == PackageManager.PERMISSION_GRANTED

}