package com.example.dicodingevents

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevents.data.remote.response.ListEventsItem
import com.example.dicodingevents.databinding.EventCardBinding
import com.example.dicodingevents.ui.eventdetail.EventDetailActivity

class EventAdapter(private val events: List<ListEventsItem>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    class EventViewHolder(private val binding: EventCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
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
                intent.putExtra("EVENT_ID", event.id.toString())
                intent.putExtra("EVENT_LINK", event.link)
                itemView.context.startActivity(intent)
            }
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