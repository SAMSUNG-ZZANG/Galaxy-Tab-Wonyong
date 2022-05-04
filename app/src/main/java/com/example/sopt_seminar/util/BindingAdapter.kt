package com.example.sopt_seminar.util

import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sopt_seminar.R

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["fragmentManger", "childFragment"])
    fun changeFragment(button: Button, fragmentManger: FragmentManager, childFragment: Fragment) {
        button.setOnClickListener {
            fragmentManger.beginTransaction().replace(R.id.home_fragment_cv, childFragment).commit()
        }
    }
}