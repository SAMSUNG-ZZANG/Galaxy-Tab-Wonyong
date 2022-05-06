package com.example.sopt_seminar.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    val fragmentList= mutableListOf<Fragment>()

    override fun getItemCount() =fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]

}