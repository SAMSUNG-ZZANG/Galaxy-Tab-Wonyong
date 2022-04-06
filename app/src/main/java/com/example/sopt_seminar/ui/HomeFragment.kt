package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.HomeActivtyBinding

class HomeFragment : Fragment() {
    lateinit var binding: HomeActivtyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.home_activty, container, false)
        return binding.root
    }
}