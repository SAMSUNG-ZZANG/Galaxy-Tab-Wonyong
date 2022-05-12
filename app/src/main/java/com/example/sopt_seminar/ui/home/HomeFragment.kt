package com.example.sopt_seminar.ui.home

import android.os.Bundle
import android.view.View
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.FragmentHomeBinding
import com.example.sopt_seminar.util.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var adapter: HomeViewPagerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            img = "https://avatars.githubusercontent.com/u/82709044?v=4"
        }
        initAdapter()
        initTabLayout()
    }

    private fun initAdapter() {
        adapter =
            HomeViewPagerAdapter(requireActivity().supportFragmentManager.fragmentFactory, this)
        binding.homeVp.adapter = adapter
    }

    private fun initTabLayout() {
        val tabList = resources.getStringArray(R.array.tab_layout_items)

        TabLayoutMediator(binding.homeTl, binding.homeVp) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }
}