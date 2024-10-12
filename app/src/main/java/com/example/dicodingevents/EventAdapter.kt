package com.example.dicodingevents

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevents.data.response.ListEventsItem
import com.example.dicodingevents.databinding.EventCardBinding

class EventAdapter(private val events: List<ListEventsItem>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    class EventViewHolder(private val binding: EventCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.imgEvent)

            binding.tvLocation.text = event.cityName
            binding.tvEventStart.text = event.beginTime
            binding.tvEventEnd.text = event.endTime
            binding.tvEventTitle.text = event.name
            binding.tvEventOwner.text = event.ownerName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }


    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }
}