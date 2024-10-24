package com.example.dicodingevents

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevents.EventListAdapter.Companion.DIFF_CALLBACK
import com.example.dicodingevents.data.local.entity.EventEntity
import com.example.dicodingevents.data.remote.response.ListEventsItem
import com.example.dicodingevents.databinding.EventCardBinding
import com.example.dicodingevents.ui.eventdetail.EventDetailActivity

class EventAdapter() :
    ListAdapter<EventEntity, EventAdapter.EventViewHolder>(DIFF_CALLBACK) {

    class EventViewHolder(private val binding: EventCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            Glide.with(itemView.context)
                .load(event.mediaCover)
                .into(binding.imgEvent)

            binding.tvLocation.text = event.cityName
            binding.tvEventStart.text = event.beginTime
            binding.tvEventEnd.text = event.endTime
            binding.tvEventTitle.text = event.name
            binding.tvEventOwner.text = event.ownerName

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, EventDetailActivity::class.java)
                intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, event.eventId)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<EventEntity> =
            object : DiffUtil.ItemCallback<EventEntity>() {
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
    }

}