package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.SignInActivityBinding
import com.example.sopt_seminar.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {
    lateinit var binding: SignInActivityBinding
    private val viewModel: SignInViewModel by activityViewModels()
    private val args: SignInFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.sign_in_activity, container, false)
        with(binding) {
            activity = this@SignInFragment
            id = args.userId
            password = args.userPassword

            signInLoginBtn.setOnClickListener {
                lifecycleScope.launch {
                    when (viewModel.checkInput(
                        signInIdEt.text.toString(),
                        signInPasswordEt.text.toString()
                    )) {
                        0 -> Toast.makeText(context, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
                        1 -> Toast.makeText(context, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                        2 -> Toast.makeText(context, "유효한 아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                        3 -> Toast.makeText(context, "유효한 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                        4 -> Toast.makeText(context, "아이디 비밀번호를 다시 입력해주세요.", Toast.LENGTH_SHORT)
                            .show()
                        5 -> findNavController().navigate(R.id.home_fragment)
                    }
                }
            }
        }
        return binding.root
    }

    fun goMainActivity() {
        findNavController().navigate(R.id.sign_up_fragment)
    }
}