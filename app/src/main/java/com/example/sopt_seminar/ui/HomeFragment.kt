package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.View
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.HomeFragmentBinding
import com.example.sopt_seminar.util.BaseFragment

class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {
    private val followerFragment = FollowerFragment()
    private val repoFragment = RepoFragment()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().replace(R.id.home_fragment_cv, followerFragment)
            .commit()
        binding.followerBtn.isSelected = true

        binding.apply {
            repo = repoFragment
            follower = followerFragment
            fragmentManager = childFragmentManager
            main = this@HomeFragment
            drawable = R.drawable.kang
        }
    }

    val changeState = fun(view: View) {
        val followerBtn = binding.followerBtn
        val repoBtn = binding.repoBtn
        when (view.id) {
            R.id.follower_btn -> {
                if (!followerBtn.isSelected) {
                    followerBtn.isSelected = true
                    repoBtn.isSelected = false
                }
            }
            R.id.repo_btn -> {
                if (!repoBtn.isSelected) {
                    repoBtn.isSelected = true
                    followerBtn.isSelected = false
                }
            }
        }
    }
}