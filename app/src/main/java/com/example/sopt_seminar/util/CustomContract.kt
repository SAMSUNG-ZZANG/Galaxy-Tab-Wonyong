package com.example.sopt_seminar.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

object CustomContract {
    class GetPhoto : ActivityResultContract<Void, Uri?>() {
        override fun createIntent(context: Context, input: Void?) =
            Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return intent?.data
        }
    }
}