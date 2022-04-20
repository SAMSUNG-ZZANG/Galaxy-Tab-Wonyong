package com.example.sopt_seminar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sopt_seminar.databinding.FollowerFrameBinding
import com.example.sopt_seminar.domain.model.Follower
import java.util.*

class FollowerAdapter: ListAdapter<Follower, FollowerAdapter.FollowerViewHolder>(FOLLOWER_COMPARATOR){

    private lateinit var itemClick: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        itemClick = listener
    }

    class FollowerViewHolder(binding: FollowerFrameBinding,listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        private val name = binding.followerNameTv
        private val des = binding.followerDesTv
        private val root = binding.followerFrame

        init {
            root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(_name: String, _des: String) {
            name.text = _name
            des.text = _des
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view = FollowerFrameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(view,itemClick)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val current = getItem(position)
        with(holder) {
            bind(current.name, current.description)
        }
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val newList = currentList.toMutableList()
        Collections.swap(newList, fromPosition, toPosition)
        submitList(newList)
    }

    fun removeItem(position:Int){
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