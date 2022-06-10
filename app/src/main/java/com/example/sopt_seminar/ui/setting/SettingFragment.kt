package com.example.sopt_seminar.ui.setting

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.FragmentSettingBinding
import com.example.sopt_seminar.domain.usecase.SetAutoLoginUseCase
import com.example.sopt_seminar.util.BaseFragment
import com.example.sopt_seminar.util.BaseToast.showToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    @Inject
    lateinit var setAutoLoginUseCase: SetAutoLoginUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingImgBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(resources.getString(R.string.setting_dialog_text))
                .setPositiveButton(resources.getString(R.string.setting_dialog_positive)) { _, _ ->
                    lifecycleScope.launch {
                        requireContext().showToast(resources.getString(R.string.setting_auto_login))
                        setAutoLoginUseCase(false)
                    }
                }
                .show()
        }
    }
}