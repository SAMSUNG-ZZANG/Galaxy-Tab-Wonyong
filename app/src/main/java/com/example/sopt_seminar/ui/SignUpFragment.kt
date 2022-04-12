package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.SignUpFragmentBinding
import com.example.sopt_seminar.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: SignUpFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.sign_up_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            activity = this@SignUpFragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun signUp(name: String, id: String, password: String) {
        viewModel.checkInput(id,password,name)
        viewModel.isError.observe(this, Observer { isError->
            if(isError){
                viewModel.showErrorToast.observe(this, Observer {
                    it.getContentIfNotHandled()?.let {
                        Toast.makeText(context, viewModel.errorMsg.value, Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(
                    userId = id,
                    userPassword = password
                )
                findNavController().navigate(action)
            }
        })
    }
}