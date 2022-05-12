package com.example.sopt_seminar.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setDrawable")
    fun ImageView.showImg(drawable: String) {
        load(drawable) {
            transformations(CircleCropTransformation())
        }
    }
}