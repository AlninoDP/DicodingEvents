package com.example.dicodingevents.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevents.R
import com.example.dicodingevents.data.local.entity.EventEntity
import com.example.dicodingevents.databinding.EventListItemBinding
import com.example.dicodingevents.ui.eventdetail.EventDetailActivity

class EventListAdapter(private val onBookmarkClick: (EventEntity) -> Unit) :
    ListAdapter<EventEntity, EventListAdapter.EventListViewHolder>(DIFF_CALLBACK) {

    class EventListViewHolder(val binding: EventListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.imgEventCover)

            binding.tvHomeEventTitle.text = event.name
            binding.tvSummary.text = event.summary

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, EventDetailActivity::class.java)
                intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, event.eventId)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val binding =
            EventListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)


        val ivBookmark = holder.binding.listBookmark
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