package com.example.sopt_seminar.ui.camera


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.ContextCompat
import coil.load
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.FragmentCameraBinding
import com.example.sopt_seminar.util.BaseFragment
import com.example.sopt_seminar.util.CustomContract


class CameraFragment : BaseFragment<FragmentCameraBinding>(R.layout.fragment_camera) {
    private val activityResultLauncher =
        registerForActivityResult(CustomContract.GetPhoto()) { uri ->
            if (uri != null) binding.cameraImg.load(uri)
        }
    
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                activityResultLauncher.launch()
            } else {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) activityResultLauncher.launch()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cameraBtn.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}