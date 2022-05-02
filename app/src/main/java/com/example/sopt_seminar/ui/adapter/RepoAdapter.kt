package com.example.sopt_seminar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sopt_seminar.databinding.RepoFrameBinding
import com.example.sopt_seminar.domain.model.Repo
import java.util.*

class RepoAdapter(private val action: (Int) -> Unit) :
    ListAdapter<Repo, RepoAdapter.RepoViewHolder>(REPO_COMPARATOR) {

    inner class RepoViewHolder(private val binding: RepoFrameBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { action(adapterPosition) }
        }

        fun bind(repo: Repo) {
            binding.repo = repo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = RepoFrameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
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