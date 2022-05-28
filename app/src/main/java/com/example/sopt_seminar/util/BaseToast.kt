package com.example.sopt_seminar.util

import android.content.Context
import android.widget.Toast

object BaseToast {
    fun Context.showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}