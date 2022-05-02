package com.example.sopt_seminar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sopt_seminar.databinding.FollowerFrameBinding
import com.example.sopt_seminar.domain.model.Follower
import java.util.*

class FollowerAdapter(private val action: (Int) -> Unit) :
    ListAdapter<Follower, FollowerAdapter.FollowerViewHolder>(FOLLOWER_COMPARATOR) {

    inner class FollowerViewHolder(private val binding: FollowerFrameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { action(adapterPosition) }
        }

        fun bind(follower: Follower) {
            binding.follower = follower
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view = FollowerFrameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        view.followerFrame.setOnClickListener { currentList[itemCount] }
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val newList = currentList.toMutableList()
        Collections.swap(newList, fromPosition, toPosition)
        submitList(newList)
    }

    fun removeItem(position: Int) {
        val newList = currentList.toMutableList()
        newList.removeAt(position)
        submitList(newList)
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