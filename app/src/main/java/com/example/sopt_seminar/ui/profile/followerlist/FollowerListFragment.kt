package com.example.sopt_seminar.ui.profile.followerlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.FollowerListFragmentBinding
import com.example.sopt_seminar.ui.MainFragmentDirections
import com.example.sopt_seminar.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowerListFragment :
    BaseFragment<FollowerListFragmentBinding>(R.layout.follower_list_fragment) {
    private val adapter = FollowerListAdapter { name, description, img ->
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(
            followerName = name,
            followerDes = description,
            followerImg = img
        )
        findNavController().navigate(action)
    }
    private val viewModel: FollowerListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, null)
        binding.apply {
            vm = viewModel
        }
        binding.followerRecyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun handleEvent(followerListEvent: FollowerListEvent) {
        when (followerListEvent) {
            is FollowerListEvent.ShowToast -> Toast.makeText(
                context,
                followerListEvent.msg,
                Toast.LENGTH_SHORT
            ).show()
            is FollowerListEvent.FollowerList -> adapter.submitList(followerListEvent.data)
        }
    }
}