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

        binding.apply {
            repo = repoFragment
            follower = followerFragment
            fragmentManager = childFragmentManager
        }
    }
}