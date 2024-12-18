package com.example.dicodingevents.ui.eventdetail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dicodingevents.R
import com.example.dicodingevents.data.local.entity.EventEntity
import com.example.dicodingevents.databinding.ActivityEventDetailBinding
import com.example.dicodingevents.ui.ViewModelFactory
import java.util.Locale

class EventDetailActivity : AppCompatActivity() {

    private var _binding: ActivityEventDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }

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


        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val eventDetailViewModel by viewModels<EventDetailViewModel> {
            factory
        }

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, 0)


        eventDetailViewModel.getEventData(eventId).observe(this) {
            val eventLink = it.link
            setEventData(it)
            binding.btnRegister.setOnClickListener{
                val openUrlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(eventLink))
                startActivity(openUrlIntent)
            }
        }




    }


    private fun setEventData(eventData: EventEntity) {
        Glide.with(this)
            .load(eventData.mediaCover)
            .into(binding.imgPosterEvent)

        val totalQuota = eventData.quota
        val totalRegistrant = eventData.registrants
        val remainingQuota = totalQuota - totalRegistrant

        binding.tvEventDetailTitle.text = eventData.name
        binding.tvEventDetailStart.text = eventData.beginTime
        binding.tvQuota.text = String.format(Locale.getDefault(),"%d", totalQuota)
        binding.tvRegistrant.text = String.format(Locale.getDefault(),"%d", totalRegistrant)
        binding.tvQuotaRemain.text = getString(R.string.remaining_quota_text, remainingQuota)
        binding.tvOwnerName.text = eventData.ownerName
        binding.tvEventDescription.text = HtmlCompat.fromHtml(
            eventData.description.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY,
        )
    }


}