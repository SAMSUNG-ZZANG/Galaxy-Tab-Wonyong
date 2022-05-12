package com.example.sopt_seminar.ui.profile.followerlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sopt_seminar.databinding.FollowerFrameBinding
import com.example.sopt_seminar.domain.model.Follower
import java.util.*

class FollowerListAdapter(private val action: (String, String, String) -> Unit) :
    ListAdapter<Follower, FollowerListAdapter.FollowerViewHolder>(FOLLOWER_COMPARATOR) {

    class FollowerViewHolder(
        private val binding: FollowerFrameBinding,
        private val action: (String, String, String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(follower: Follower) {
            binding.follower = follower
            binding.root.setOnClickListener {
                action(
                    follower.name,
                    follower.description,
                    follower.profile
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view = FollowerFrameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(view, action)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    fun moveItem(fromPosition: Int, toPosition: Int, move: () -> Unit) {
        val newList = currentList.toMutableList()
        Collections.swap(newList, fromPosition, toPosition)
        submitList(newList)
        move()
    }

    fun removeItem(position: Int, remove: () -> Unit) {
        val newList = currentList.toMutableList()
        newList.removeAt(position)
        submitList(newList)
        remove()
    }

    companion object {
        private val FOLLOWER_COMPARATOR = object : DiffUtil.ItemCallback<Follower>() {
            override fun areItemsTheSame(oldItem: Follower, newItem: Follower): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Follower, newItem: Follower): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}