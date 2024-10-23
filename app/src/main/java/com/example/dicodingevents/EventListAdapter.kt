package com.example.dicodingevents

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevents.data.remote.response.ListEventsItem
import com.example.dicodingevents.databinding.EventListItemBinding
import com.example.dicodingevents.ui.eventdetail.EventDetailActivity

class EventListAdapter(private val events: List<ListEventsItem>) :
    RecyclerView.Adapter<EventListAdapter.EventListViewHolder>() {
    class EventListViewHolder(private val binding: EventListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.imgEventCover)

            binding.tvHomeEventTitle.text = event.name
            binding.tvSummary.text = event.summary

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, EventDetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id.toString())
                intent.putExtra("EVENT_LINK", event.link)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val binding = EventListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventListViewHolder(binding)
    }


    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

}