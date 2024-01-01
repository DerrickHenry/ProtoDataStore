package com.example.protodatastore.users.views.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.protodatastore.users.api.models.User

class UserDiffUtil : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.name == newItem.name
    }
}