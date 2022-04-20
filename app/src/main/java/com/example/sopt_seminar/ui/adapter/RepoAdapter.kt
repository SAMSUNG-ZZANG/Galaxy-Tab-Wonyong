package com.example.sopt_seminar.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sopt_seminar.databinding.RepoFrameBinding
import com.example.sopt_seminar.domain.model.Repo
import java.util.*

class RepoAdapter : ListAdapter<Repo, RepoAdapter.RepoViewHolder>(REPO_COMPARATOR) {
    private lateinit var itemClick: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        itemClick = listener
    }

    class RepoViewHolder(binding: RepoFrameBinding,listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        private val name = binding.repoNameTv
        private val des = binding.repoDesTv
        private val root = binding.repoFrame

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = RepoFrameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(view,itemClick)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
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
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}