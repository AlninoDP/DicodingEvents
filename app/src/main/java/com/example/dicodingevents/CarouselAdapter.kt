package com.example.dicodingevents

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevents.data.remote.response.ListEventsItem
import com.example.dicodingevents.databinding.CarouselItemBinding
import com.example.dicodingevents.ui.eventdetail.EventDetailActivity

class CarouselAdapter(private val events: List<ListEventsItem>) :
    RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {
    class CarouselViewHolder(private val binding: CarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(itemView.context)
                .load(event.mediaCover)
                .into(binding.carouselImage)

            binding.eventName.text = event.name

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, EventDetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id.toString())
                intent.putExtra("EVENT_LINK", event.link)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }


    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

}