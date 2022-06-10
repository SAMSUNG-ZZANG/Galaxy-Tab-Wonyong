package com.example.sopt_seminar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.sopt_seminar.databinding.ActivityMainBinding
import com.example.sopt_seminar.domain.usecase.IsAutoLoginUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var isAutoLoginUseCase: IsAutoLoginUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        installSplashScreen()

        lifecycleScope.launch {
            isAutoLoginUseCase().collect { isAutoLogin ->
                if (isAutoLogin) findNavController(R.id.nav_host_fragment).setGraph(R.navigation.main_nav_graph)
                else findNavController(R.id.nav_host_fragment).setGraph(R.navigation.login_nav_graph)
            }
        }
        setContentView(binding.root)
    }
}