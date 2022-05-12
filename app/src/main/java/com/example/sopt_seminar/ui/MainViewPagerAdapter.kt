package com.example.sopt_seminar.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sopt_seminar.ui.camera.CameraFragment
import com.example.sopt_seminar.ui.home.HomeFragment
import com.example.sopt_seminar.ui.profile.ProfileFragment

class MainViewPagerAdapter(private val factory: FragmentFactory, private val fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val classLoader = fragment.requireActivity().classLoader
        return when (position) {
            0 -> factory.instantiate(classLoader, ProfileFragment::class.java.name)
            1 -> factory.instantiate(classLoader, HomeFragment::class.java.name)
            2 -> factory.instantiate(classLoader, CameraFragment::class.java.name)
            else -> throw RuntimeException()
        }
    }
}