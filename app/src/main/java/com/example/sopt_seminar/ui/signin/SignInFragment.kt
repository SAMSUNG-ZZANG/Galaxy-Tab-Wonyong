package com.example.sopt_seminar.ui.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.SignInFragmentBinding
import com.example.sopt_seminar.util.BaseFragment
import com.example.sopt_seminar.util.BaseToast.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInFragmentBinding>(R.layout.sign_in_fragment) {
    private val viewModel: SignInViewModel by viewModels()
    private val args: SignInFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = viewModel
        }
        viewModel.setText(args.userId, args.userPassword)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    handleEvent(event)
                }
            }
        }

        binding.signInClearBtn.setOnClickListener {
            findNavController().navigate(R.id.sign_up_fragment)
        }
    }

    private fun handleEvent(event: Event) = when (event) {
        is Event.IsFinish -> {
            findNavController().popBackStack()
            findNavController().navigate(R.id.main_fragment)
        }
        is Event.ShowToast -> { requireContext().showToast(event.msg) }
    }
}