package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.DetailFragmentBinding
import com.example.sopt_seminar.util.BaseFragment

class DetailFragment : BaseFragment<DetailFragmentBinding>(R.layout.detail_fragment) {
    private val args: DetailFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            followerName = args.followerName
            followerDes = args.followerDes
        }
    }
}