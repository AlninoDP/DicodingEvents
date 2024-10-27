package com.example.dicodingevents.utils

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.dicodingevents.data.local.entity.EventEntity

class EventDiffCallback : DiffUtil.ItemCallback<EventEntity>() {
    override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
        return oldItem.name == newItem.name
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: EventEntity,
        newItem: EventEntity
    ): Boolean {
        return oldItem == newItem
    }

}