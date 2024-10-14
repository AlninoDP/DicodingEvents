package com.example.dicodingevents.ui.eventdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dicodingevents.R
import com.example.dicodingevents.data.response.ListEventsItem
import com.example.dicodingevents.databinding.ActivityEventDetailBinding
import com.google.android.material.snackbar.Snackbar

class EventDetailActivity : AppCompatActivity() {

    private var _binding: ActivityEventDetailBinding? = null
    private val binding get() = _binding!!
    private val eventDetailViewModel by viewModels<EventDetailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        val eventId = intent.getStringExtra("EVENT_ID")
        val eventLink = intent.getStringExtra("EVENT_LINK")

        eventDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        eventDetailViewModel.loadEventDetail(eventId!!)

        eventDetailViewModel.eventsItem.observe(this) {
            setEventData(it)
        }

        eventDetailViewModel.snackBarText.observe(this) { it ->
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }



        binding.btnRegister.setOnClickListener {
            val openUrlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(eventLink))
            startActivity(openUrlIntent)
        }


    }

    private fun setEventData(eventData: ListEventsItem) {
        Glide.with(this)
            .load(eventData.mediaCover)
            .into(binding.imgPosterEvent)

        binding.tvEventDetailTitle.text = eventData.name
        binding.tvEventDetailStart.text = eventData.beginTime
        binding.tvQuota.text = eventData.quota.toString()
        binding.tvRegistrant.text = eventData.registrants.toString()
        binding.tvOwnerName.text = eventData.ownerName
        binding.tvEventDescription.text = HtmlCompat.fromHtml(
            eventData.description.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY,
        )
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
            binding.detailContent.visibility = View.GONE // Hide the main content
        } else {
            binding.progressBar3.visibility = View.GONE
            binding.detailContent.visibility = View.VISIBLE // Hide the main content
        }
    }
}