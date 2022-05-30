package com.example.sopt_seminar.ui.setting

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.FragmentSettingBinding
import com.example.sopt_seminar.domain.usecase.SetAutoLoginUseCase
import com.example.sopt_seminar.util.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject
import kotlinx.coroutines.launch

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    @Inject
    lateinit var setAutoLoginUseCase: SetAutoLoginUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingImgBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage("자동로그인 해제?")
                .setPositiveButton("해제") { _, _ ->
                    lifecycleScope.launch {
                        setAutoLoginUseCase(false)
                    }
                }
                .show()
        }
    }
}