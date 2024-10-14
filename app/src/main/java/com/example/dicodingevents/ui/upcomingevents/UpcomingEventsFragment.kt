package com.example.dicodingevents.ui.upcomingevents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevents.EventAdapter
import com.example.dicodingevents.data.response.ListEventsItem
import com.example.dicodingevents.databinding.FragmentUpcomingEventsBinding
import com.google.android.material.snackbar.Snackbar

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


        //layout manager
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUpcomingEvents.layoutManager = layoutManager


        // Observer
        upcomingEventsViewModel.listEventsItem.observe(viewLifecycleOwner) {
            setEventsData(it)
        }
        upcomingEventsViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        upcomingEventsViewModel.snackBarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    binding.rvUpcomingEvents,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }



        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setEventsData(events: List<ListEventsItem>) {
        val adapter = EventAdapter(events)
        binding.rvUpcomingEvents.adapter = adapter
    }

    /// Show progress bar
    private fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }


}