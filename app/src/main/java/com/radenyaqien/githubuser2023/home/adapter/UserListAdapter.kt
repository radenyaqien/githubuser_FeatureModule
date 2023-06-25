package com.radenyaqien.githubuser2023.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.CircleCropTransformation
import com.radenyaqien.core.domain.model.GithubUser
import com.radenyaqien.githubuser2023.R
import com.radenyaqien.githubuser2023.databinding.ItemUserBinding

class UserListAdapter(
    private val onclick: (GithubUser) -> Unit
) : ListAdapter<GithubUser, UserListAdapter.UsersViewHolder>(DIFFER) {

    companion object {

        val DIFFER = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, onclick)
    }

    class UsersViewHolder(private val binding: ItemUserBinding) : ViewHolder(binding.root) {

        fun bind(user: GithubUser, onclick: (GithubUser) -> Unit) {

            binding.txtId.text = user.id.toString()
            binding.txtUsername.text = user.username
            binding.imgUser.load(user.avatarUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }
            binding.root.setOnClickListener {
                onclick(user)
            }
        }
    }


}