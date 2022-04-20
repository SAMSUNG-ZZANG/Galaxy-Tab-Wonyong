package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.View
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.HomeFragmentBinding
import com.example.sopt_seminar.util.BaseFragment
import com.google.android.material.tabs.TabLayout

class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val followerFragment = FollowerFragment()
        val repoFragment = RepoFragment()
        childFragmentManager.beginTransaction().replace(R.id.home_fragment_cv, followerFragment)
            .commit()

        with(binding) {
            homeTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val transaction = childFragmentManager.beginTransaction()
                    when (tab.position) {
                        FOLLOWER_TURN -> transaction.replace(R.id.home_fragment_cv, followerFragment)
                        REPOSITORY_TURN -> transaction.replace(R.id.home_fragment_cv, repoFragment)
                    }
                    transaction.commit()
                }
                override fun onTabUnselected(tab: TabLayout.Tab) = Unit
                override fun onTabReselected(tab: TabLayout.Tab) = Unit
            })
        }
    }

    companion object {
        const val FOLLOWER_TURN = 0
        const val REPOSITORY_TURN = 1
    }
}