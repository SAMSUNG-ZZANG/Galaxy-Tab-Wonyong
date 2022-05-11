package com.example.sopt_seminar.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.ProfileFragmentBinding
import com.example.sopt_seminar.ui.profile.followerlist.FollowerListFragment
import com.example.sopt_seminar.ui.profile.repo.RepoFragment
import com.example.sopt_seminar.util.BaseFragment

class ProfileFragment : BaseFragment<ProfileFragmentBinding>(R.layout.profile_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.commit {
            replace<FollowerListFragment>(R.id.home_fragment_cv)
        }
        binding.followerBtn.isSelected = true

        binding.apply {
            drawable = R.drawable.kang
        }

        with(binding) {
            followerBtn.setOnClickListener { followerBtnEvent() }
            repoBtn.setOnClickListener { repoBtnEvent() }
        }
    }

    private fun followerBtnEvent() {
        if (!binding.followerBtn.isSelected) {
            binding.followerBtn.isSelected = true
            binding.repoBtn.isSelected = false
            childFragmentManager.commit {
                replace<FollowerListFragment>(R.id.home_fragment_cv)
            }
        }
    }

    private fun repoBtnEvent() {
        if (!binding.repoBtn.isSelected) {
            binding.repoBtn.isSelected = true
            binding.followerBtn.isSelected = false
            childFragmentManager.commit {
                replace<RepoFragment>(R.id.home_fragment_cv)
            }
        }
    }
}