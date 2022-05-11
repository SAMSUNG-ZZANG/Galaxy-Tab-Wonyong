package com.example.sopt_seminar.util

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setDrawable")
    fun ImageView.showImg(drawable: Int) {
        load(drawable) {
            transformations(CircleCropTransformation())
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["isError", "errorMsg"])
    fun View.showToast(isError: Boolean, errorMsg: String) {
        if (isError) Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
    }
}