package com.example.dicodingevents.ui.upcomingevents

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
        (activity as AppCompatActivity).supportActionBar?.hide()


        //layout manager
        val layoutManager = LinearLayoutManager(requireActivity())
        val searchResultLayoutManager = LinearLayoutManager(requireActivity())
        binding.rvUpcomingEvents.layoutManager = layoutManager
        binding.rvSearchResultsUpcoming.layoutManager = searchResultLayoutManager


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

        searchEvent()
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setEventsData(events: List<ListEventsItem>) {
        val adapter = EventAdapter(events)
        binding.rvUpcomingEvents.adapter = adapter
        binding.rvSearchResultsUpcoming.adapter = adapter
    }

    /// Show progress bar
    private fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.pbSearchResultUpcoming.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.pbSearchResultUpcoming.visibility = View.INVISIBLE
        }
    }

    private fun searchEvent() {
        with(binding) {
            searchViewUpcoming.setupWithSearchBar(upcomingSearchBar)

            searchViewUpcoming.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    upcomingSearchBar.setText(searchViewUpcoming.text)
                    upcomingEventsViewModel.searchUpcomingEvents(s.toString())
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            searchViewUpcoming.editText.setOnEditorActionListener { _, _, _ ->
                upcomingSearchBar.setText(searchViewUpcoming.text)
                upcomingEventsViewModel.searchUpcomingEvents(searchViewUpcoming.text.toString())
                false
            }
        }
    }

}