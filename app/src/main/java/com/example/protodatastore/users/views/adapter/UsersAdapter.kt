package com.example.protodatastore.users.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.protodatastore.R
import com.example.protodatastore.databinding.ItemUserBinding
import com.example.protodatastore.users.api.models.User

/**
 * Adapter to display all the posts in the stream
 * @param onUserClicked user selection listener lambda
 */
class UsersAdapter(
        private val onUserClicked: (user: User) -> Unit
) : ListAdapter<User, UsersAdapter.ViewHolder>(UserDiffUtil()) {

    inner class ViewHolder(private val binding: ItemUserBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.name.text = user.name?.last.plus(", ").plus(user.name?.first)
            Glide
                    .with(binding.profilePic)
                    .load(user.picture?.large)
                    .placeholder(R.drawable.ic_downloading_black_24dp)
                    .error(R.drawable.ic_error_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .into(binding.profilePic)
            binding.root.setOnClickListener { onUserClicked.invoke(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}