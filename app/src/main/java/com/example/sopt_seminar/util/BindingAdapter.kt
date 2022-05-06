package com.example.sopt_seminar.util

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sopt_seminar.R
import com.example.sopt_seminar.domain.model.Follower
import com.example.sopt_seminar.domain.model.Repo
import com.example.sopt_seminar.ui.adapter.FollowerAdapter
import com.example.sopt_seminar.ui.adapter.RepoAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["fragmentManger", "childFragment", "changeSelectState"])
    fun changeFragment(
        button: Button,
        fragmentManger: FragmentManager,
        childFragment: Fragment,
        changeSelectState: (View) -> Unit
    ) {
        button.setOnClickListener {
            fragmentManger.beginTransaction().replace(R.id.home_fragment_cv, childFragment).commit()
            changeSelectState(button)
        }
    }

    @JvmStatic
    @BindingAdapter("setDrawable")
    fun showImg(imageView: ImageView, drawable: Int) {
        Glide.with(imageView.context)
            .load(drawable)
            .override(100, 100)
            .circleCrop()
            .error(R.drawable.ic_baseline_person_24)
            .into(imageView)
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