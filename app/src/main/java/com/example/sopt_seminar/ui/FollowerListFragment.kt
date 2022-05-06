package com.example.sopt_seminar.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.FollowerListFragmentBinding
import com.example.sopt_seminar.domain.model.Follower
import com.example.sopt_seminar.ui.adapter.FollowerAdapter
import com.example.sopt_seminar.util.BaseFragment
import java.util.*

class FollowerListFragment :
    BaseFragment<FollowerListFragmentBinding>(R.layout.follower_list_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, null)
        val adapter = FollowerAdapter { name, description ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(
                followerName = name,
                followerDes = description
            )
            findNavController().navigate(action)
        }

        binding.apply {
            main = this@FollowerListFragment
            followerRecyclerView.adapter = adapter
        }
    }

    val removeItem = fun(position: Int) {
        testList.removeAt(position)
    }

    val moveItem = fun(fromPosition: Int, toPosition: Int) {
        Collections.swap(testList, fromPosition, toPosition)
    }

    fun getList(): List<Follower> = testList

    companion object {
        private val testList = mutableListOf(
            Follower(
                0,
                R.drawable.kang,
                "팔로우1",
                "팔로우1 입니다.팔로우1 입니다.팔로우1 입니다.팔로우1 입니다.팔로우1 입니다.팔로우1 입니다."
            ),
            Follower(1, R.drawable.kang, "팔로우2", "팔로우2 입니다.".repeat(5)),
            Follower(2, R.drawable.kang, "팔로우3", "팔로우3 입니다."),
            Follower(3, R.drawable.kang, "팔로우4", "팔로우4 입니다."),
            Follower(4, R.drawable.kang, "팔로우5", "팔로우5 입니다."),
            Follower(5, R.drawable.kang, "팔로우6", "팔로우6 입니다."),
            Follower(6, R.drawable.kang, "팔로우7", "팔로우7 입니다."),
            Follower(7, R.drawable.kang, "팔로우8", "팔로우8 입니다.".repeat(5)),
            Follower(8, R.drawable.kang, "팔로우9", "팔로우9 입니다."),
            Follower(9, R.drawable.kang, "팔로우10", "팔로우10 입니다."),
            Follower(10, R.drawable.kang, "팔로우11", "팔로우11 입니다."),
            Follower(11, R.drawable.kang, "팔로우12", "팔로우12 입니다."),
            Follower(12, R.drawable.kang, "팔로우13", "팔로우13 입니다."),
            Follower(13, R.drawable.kang, "팔로우14", "팔로우14 입니다."),
            Follower(14, R.drawable.kang, "팔로우15", "팔로우15 입니다."),
            Follower(15, R.drawable.kang, "팔로우16", "팔로우16 입니다."),
            Follower(16, R.drawable.kang, "팔로우17", "팔로우17 입니다."),
            Follower(17, R.drawable.kang, "팔로우18", "팔로우18 입니다."),
            Follower(18, R.drawable.kang, "팔로우19", "팔로우19 입니다."),
            Follower(19, R.drawable.kang, "팔로우20", "팔로우20 입니다."),
        )
    }
}