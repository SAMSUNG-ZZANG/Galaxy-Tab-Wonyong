package com.example.sopt_seminar.util

import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.sopt_seminar.R
import com.example.sopt_seminar.domain.model.Follower
import com.example.sopt_seminar.domain.model.Repo
import com.example.sopt_seminar.ui.adapter.FollowerAdapter
import com.example.sopt_seminar.ui.adapter.RepoAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["fragmentManger", "childFragment"])
    fun changeFragment(button: Button, fragmentManger: FragmentManager, childFragment: Fragment) {
        button.setOnClickListener {
            fragmentManger.beginTransaction().replace(R.id.home_fragment_cv, childFragment).commit()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["bindList", "moveItem", "removeItem"])
    fun bindList(
        recyclerView: RecyclerView,
        bindList: List<*>,
        moveItem: (Int, Int) -> Unit,
        removeItem: (Int) -> Unit
    ) {
        when (recyclerView.adapter) {
            is FollowerAdapter -> {
                val adapter = recyclerView.adapter as FollowerAdapter
                val copyBindList = bindList.toCollection(mutableListOf()) as List<Follower>
                val simpleCallback = getSimpleCallBack(adapter, moveItem, removeItem)
                ItemTouchHelper(simpleCallback!!).attachToRecyclerView(recyclerView)
                adapter.submitList(copyBindList)
            }
            is RepoAdapter -> {
                val adapter = recyclerView.adapter as RepoAdapter
                val copyBindList = bindList.toCollection(mutableListOf()) as List<Repo>
                val simpleCallback = getSimpleCallBack(adapter, moveItem, removeItem)
                ItemTouchHelper(simpleCallback!!).attachToRecyclerView(recyclerView)
                adapter.submitList(copyBindList)
            }
        }
    }

    private fun getSimpleCallBack(
        adapter: RecyclerView.Adapter<*>,
        moveItem: (Int, Int) -> Unit,
        removeItem: (Int) -> Unit
    ): ItemTouchHelper.SimpleCallback? {
        var simpleCallback: ItemTouchHelper.SimpleCallback? = null
        when (adapter) {
            is FollowerAdapter -> {
                simpleCallback = object : ItemTouchHelper.SimpleCallback(
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
            }
            is RepoAdapter -> {
                simpleCallback = object : ItemTouchHelper.SimpleCallback(
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
            }
        }
        return simpleCallback
    }
}