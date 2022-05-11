package com.example.sopt_seminar.ui.profile.repo

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.sopt_seminar.R
import com.example.sopt_seminar.databinding.RepoFragmentBinding
import com.example.sopt_seminar.domain.model.Repo
import com.example.sopt_seminar.ui.MainFragmentDirections
import com.example.sopt_seminar.util.BaseFragment
import java.util.*

class RepoFragment : BaseFragment<RepoFragmentBinding>(R.layout.repo_fragment) {
    private val adapter = RepoAdapter { name, description ->
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(
            followerName = name,
            followerDes = description
        )
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        val simpleCallback =
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.START or ItemTouchHelper.END,
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
                        Collections.swap(testList, startPosition, endPosition)
                    }
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when (direction) {
                        ItemTouchHelper.LEFT -> {
                            adapter.removeItem(viewHolder.adapterPosition) {
                                testList.removeAt(viewHolder.adapterPosition)
                            }
                        }
                    }
                }
            }
        binding.repoRecyclerView.adapter = adapter
        ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.repoRecyclerView)
        adapter.submitList(testList.toList())
    }

    companion object {
        private val testList = mutableListOf(
            Repo(0, "레포1", "레포1 입니다.레포1 입니다.레포1 입니다.레포1 입니다."),
            Repo(1, "레포2", "레포2 입니다.레포2 입니다.레포2 입니다.레포2 입니다."),
            Repo(2, "레포3", "레포3 입니다."),
            Repo(3, "레포4", "레포4 입니다."),
            Repo(4, "레포5", "레포5 입니다."),
            Repo(5, "레포6", "레포6 입니다."),
            Repo(6, "레포7", "레포7 입니다."),
            Repo(7, "레포8", "레포8 입니다.레포8 입니다.레포8 입니다.레포8 입니다."),
            Repo(8, "레포9", "레포9 입니다."),
            Repo(9, "레포10", "레포10 입니다."),
            Repo(10, "레포11", "레포11 입니다."),
            Repo(11, "레포12", "레포12 입니다."),
            Repo(12, "레포13", "레포13 입니다."),
            Repo(13, "레포14", "레포14 입니다."),
            Repo(14, "레포15", "레포15 입니다."),
            Repo(15, "레포16", "레포16 입니다."),
            Repo(16, "레포17", "레포17 입니다."),
            Repo(17, "레포18", "레포18 입니다."),
            Repo(18, "레포19", "레포19 입니다."),
            Repo(19, "레포20", "레포20 입니다."),
        )
    }
}