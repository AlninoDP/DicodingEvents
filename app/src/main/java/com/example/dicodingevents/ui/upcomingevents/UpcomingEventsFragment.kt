package com.example.dicodingevents.ui.upcomingevents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevents.EventAdapter
import com.example.dicodingevents.data.response.ListEventsItem
import com.example.dicodingevents.databinding.FragmentUpcomingEventsBinding

class UpcomingEventsFragment : Fragment() {

    private var _binding: FragmentUpcomingEventsBinding? = null
    private val binding get() = _binding!!
    private val upcomingEventsViewModel by viewModels<UpcomingEventsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventsBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(this@UpcomingEventsFragment.context)
        binding.rvUpcomingEvents.layoutManager = layoutManager

        upcomingEventsViewModel.listEventsItem.observe(viewLifecycleOwner){
            setEventsData(it)
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setEventsData (events: List<ListEventsItem?>?){
        val adapter = EventAdapter(events)
        binding.rvUpcomingEvents.adapter = adapter
    }


}