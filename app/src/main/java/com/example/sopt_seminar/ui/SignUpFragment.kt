package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.SignUpActivityBinding
import com.example.sopt_seminar.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    lateinit var binding: SignUpActivityBinding
    private val viewModel: SignUpViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.sign_up_activity, container, false)

        with(binding) {
            activity = this@SignUpFragment
        }
        return binding.root
    }

    fun signUp(name: String, id: String, password: String) {
        when (viewModel.checkInput(id, password, name)) {
            0 -> Toast.makeText(context, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            1 -> Toast.makeText(context, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(context, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            3 -> Toast.makeText(context, "유효한 아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
            4 -> Toast.makeText(context, "유효한 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            5 -> Toast.makeText(context, "유효한 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            6 -> {
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(
                    userId = id,
                    userPassword = password
                )
                findNavController().navigate(action)
            }
        }
    }
}