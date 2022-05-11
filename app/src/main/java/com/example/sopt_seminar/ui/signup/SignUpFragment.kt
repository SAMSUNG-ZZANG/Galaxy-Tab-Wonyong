package com.example.sopt_seminar.ui.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.SignUpFragmentBinding
import com.example.sopt_seminar.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpFragmentBinding>(R.layout.sign_up_fragment) {
    val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = viewModel
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFinish.collect { isFinish ->
                    if (isFinish) {
                        val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(
                            userId = viewModel.idText.value,
                            userPassword = viewModel.pwText.value
                        )
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}