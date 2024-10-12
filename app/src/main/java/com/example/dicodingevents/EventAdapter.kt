package com.example.dicodingevents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevents.data.response.ListEventsItem
import com.example.dicodingevents.databinding.EventCardBinding

class EventAdapter(private val events: List<ListEventsItem?>?) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    companion object {
        private const val VIEW_TYPE_EVENT = 1
        private const val VIEW_TYPE_PLACEHOLDER = 2
    }

    class EventViewHolder(private val binding: EventCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem?) {
            if (event != null) {
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
    }

    class PlaceHolderViewHolder(private val binding: EventCardBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(){
                // Handle placeholder view or empty state
                binding.tvEventTitle.text = "No event data available"
                // Set placeholder image or hide the image view
                binding.imgEvent.setImageResource(R.drawable.ic_launcher_background)
            }
        }

    override fun getItemViewType(position: Int): Int {
        return if (events?.get(position) == null) {
            VIEW_TYPE_EVENT
        } else {
            VIEW_TYPE_PLACEHOLDER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun getItemCount(): Int = events?.size ?: 0

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events?.get(position)
        if (holder is EventViewHolder) {
            holder.bind(event)
        }
    }
}