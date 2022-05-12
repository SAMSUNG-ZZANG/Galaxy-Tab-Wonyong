package com.example.sopt_seminar.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeViewPagerAdapter(private val factory: FragmentFactory, private val fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val classLoader = fragment.requireActivity().classLoader
        return when (position) {
            0 -> factory.instantiate(classLoader, FollowerFragment::class.java.name)
            1 -> factory.instantiate(classLoader, FollowingFragment::class.java.name)
            else -> throw RuntimeException()
        }
    }
}