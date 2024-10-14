package com.example.dicodingevents.ui.finishedevents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevents.EventAdapter
import com.example.dicodingevents.data.response.ListEventsItem
import com.example.dicodingevents.databinding.FragmentFinishedEventsBinding
import com.google.android.material.snackbar.Snackbar

class FinishedEventsFragment : Fragment() {

    private var _binding: FragmentFinishedEventsBinding? = null
    private val binding get() = _binding!!
    private val finishedEventsViewModel by viewModels<FinishedEventsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedEventsBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.hide()

        //layout manager
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFinishedEvents.layoutManager = layoutManager

        // Observer
        finishedEventsViewModel.listEventsItem.observe(viewLifecycleOwner){
            setEventsData(it)
        }
        finishedEventsViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        finishedEventsViewModel.snackBarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    binding.rvFinishedEvents,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

       with(binding){
           searchView.setupWithSearchBar(finishedSearchBar)
       }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setEventsData (events: List<ListEventsItem>){
        val adapter = EventAdapter(events)
        binding.rvFinishedEvents.adapter = adapter
    }

    /// Show progress bar
    private fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.INVISIBLE
        }
    }
}