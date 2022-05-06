package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.SignInFragmentBinding
import com.example.sopt_seminar.ui.viewmodel.SignInViewModel
import com.example.sopt_seminar.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInFragmentBinding>(R.layout.sign_in_fragment) {
    private val viewModel: SignInViewModel by activityViewModels()
    private val args: SignInFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            activity = this@SignInFragment
            id = args.userId
            password = args.userPassword
        }
    }

    fun login(idText: String, passwordText: String) {
        viewModel.checkInput(idText, passwordText)
        viewModel.isError.observe(viewLifecycleOwner, Observer { isError ->
            if (isError) {
                viewModel.showErrorToast.observe(viewLifecycleOwner, Observer {
                    it.getContentIfNotHandled()?.let {
                        Toast.makeText(context, viewModel.errorMsg.value, Toast.LENGTH_SHORT).show()
                    }
                })
            } else findNavController().navigate(R.id.mainFragment)
        })
    }

    fun goMainActivity() {
        findNavController().navigate(R.id.sign_up_fragment)
    }
}