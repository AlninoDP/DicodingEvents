package com.example.dicodingevents.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevents.R
import com.example.dicodingevents.data.local.entity.EventEntity
import com.example.dicodingevents.databinding.EventCardBinding
import com.example.dicodingevents.ui.eventdetail.EventDetailActivity
import com.example.dicodingevents.utils.EventDiffCallback

class EventAdapter(private val onBookmarkClick: (EventEntity)-> Unit) :
    ListAdapter<EventEntity, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    class EventViewHolder(val binding: EventCardBinding) :
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

        val ivBookmark = holder.binding.cardBookmark
        if (event.isBookmarked) {
            ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    ivBookmark.context,
                    R.drawable.ic_bookmarked
                )
            )
        } else {
            ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    ivBookmark.context,
                    R.drawable.ic_bookmark
                )
            )
        }

        ivBookmark.setOnClickListener {
            onBookmarkClick(event)
        }
    }

}