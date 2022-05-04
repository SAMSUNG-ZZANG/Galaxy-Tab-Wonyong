package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.FollowerFragmentBinding
import com.example.sopt_seminar.domain.model.Follower
import com.example.sopt_seminar.ui.adapter.FollowerAdapter
import com.example.sopt_seminar.util.BaseFragment
import java.util.*

class FollowerFragment : BaseFragment<FollowerFragmentBinding>(R.layout.follower_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, null)
        val adapter = FollowerAdapter { name, description ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                followerName = name,
                followerDes = description
            )
            findNavController().navigate(action)
        }

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val startPosition = viewHolder.adapterPosition
                val endPosition = target.adapterPosition
                adapter.moveItem(startPosition, endPosition) {
                    moveItem(startPosition, endPosition)
                }
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        adapter.removeItem(viewHolder.adapterPosition) {
                            removeItem(viewHolder.adapterPosition)
                        }
                    }
                }
            }
        }

        with(binding) {
            followerRecyclerView.adapter = adapter
            ItemTouchHelper(simpleCallback).attachToRecyclerView(followerRecyclerView)
            adapter.submitList(testList.toCollection(mutableListOf()))
        }
    }

    private fun removeItem(position: Int) {
        testList.removeAt(position)
    }

    private fun moveItem(fromPosition: Int, toPosition: Int) {
        Collections.swap(testList, fromPosition, toPosition)
    }

    companion object {
        private val testList = mutableListOf(
            Follower(0, "팔로우1", "팔로우1 입니다.팔로우1 입니다.팔로우1 입니다.팔로우1 입니다.팔로우1 입니다.팔로우1 입니다."),
            Follower(1, "팔로우2", "팔로우2 입니다.팔로우2 입니다.팔로우2 입니다.팔로우2 입니다.팔로우2 입니다.팔로우2 입니다."),
            Follower(2, "팔로우3", "팔로우3 입니다."),
            Follower(3, "팔로우4", "팔로우4 입니다."),
            Follower(4, "팔로우5", "팔로우5 입니다."),
            Follower(5, "팔로우6", "팔로우6 입니다."),
            Follower(6, "팔로우7", "팔로우7 입니다."),
            Follower(7, "팔로우8", "팔로우8 입니다.팔로우8 입니다.팔로우8 입니다.팔로우8 입니다.팔로우8 입니다.팔로우8 입니다."),
            Follower(8, "팔로우9", "팔로우9 입니다."),
            Follower(9, "팔로우10", "팔로우10 입니다."),
            Follower(10, "팔로우11", "팔로우11 입니다."),
            Follower(11, "팔로우12", "팔로우12 입니다."),
            Follower(12, "팔로우13", "팔로우13 입니다."),
            Follower(13, "팔로우14", "팔로우14 입니다."),
            Follower(14, "팔로우15", "팔로우15 입니다."),
            Follower(15, "팔로우16", "팔로우16 입니다."),
            Follower(16, "팔로우17", "팔로우17 입니다."),
            Follower(17, "팔로우18", "팔로우18 입니다."),
            Follower(18, "팔로우19", "팔로우19 입니다."),
            Follower(19, "팔로우20", "팔로우20 입니다."),
        )
    }
}