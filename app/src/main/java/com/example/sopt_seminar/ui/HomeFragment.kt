package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.View
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.FragmentHomeBinding
import com.example.sopt_seminar.ui.adapter.ViewPagerAdapter
import com.example.sopt_seminar.util.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var adapter: ViewPagerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            img = R.drawable.kang
        }
        initAdapter()
        initTabLayout()
    }

    private fun initAdapter() {
        val fragmentList = listOf(FollowingFragment(), FollowerFragment())
        adapter = ViewPagerAdapter(this)
        adapter.fragmentList.addAll(fragmentList)
        binding.homeVp.adapter = adapter
    }

    private fun initTabLayout() {
        val tabList = resources.getStringArray(R.array.tab_layout_items)

        TabLayoutMediator(binding.homeTl, binding.homeVp) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }
}