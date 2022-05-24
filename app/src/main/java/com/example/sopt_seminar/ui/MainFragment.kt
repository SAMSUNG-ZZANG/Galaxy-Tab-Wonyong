package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.FragmentMainBinding
import com.example.sopt_seminar.util.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private lateinit var mainAdapter: MainViewPagerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initBottomNavi()
    }

    private fun initAdapter() {
        mainAdapter =
            MainViewPagerAdapter(requireActivity().supportFragmentManager.fragmentFactory, this)
        binding.mainVp.adapter = mainAdapter
    }

    private fun initBottomNavi() {
        binding.mainVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.mainBn.menu.getItem(position).isChecked = true
            }
        })

        binding.mainBn.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.profile_menu -> {
                    binding.mainVp.currentItem = 0
                    return@setOnItemSelectedListener true
                }
                R.id.home_menu -> {
                    binding.mainVp.currentItem = 1
                    return@setOnItemSelectedListener true
                }
                else -> {
                    binding.mainVp.currentItem = 2
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
}