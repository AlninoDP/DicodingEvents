package com.example.dicodingevents.ui.finishedevents

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
        val searchResultLayoutManager= LinearLayoutManager(requireActivity())
        binding.rvFinishedEvents.layoutManager = layoutManager
        binding.rvSearchResults.layoutManager = searchResultLayoutManager

        // Observer
        finishedEventsViewModel.listEventsItem.observe(viewLifecycleOwner) {
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

        searchEvent()


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setEventsData(events: List<ListEventsItem>) {
        val adapter = EventAdapter(events)
        binding.rvFinishedEvents.adapter = adapter
        binding.rvSearchResults.adapter = adapter
    }

    /// Show progress bar
    private fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
            binding.pbSearchResultFinished.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.INVISIBLE
            binding.pbSearchResultFinished.visibility = View.INVISIBLE
        }
    }

    private fun searchEvent() {
        with(binding) {
            searchView.setupWithSearchBar(binding.finishedSearchBar)

            searchView.editText.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    finishedSearchBar.setText(searchView.text)
                    finishedEventsViewModel.searchFinishedEvents(s.toString())
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            searchView.editText.setOnEditorActionListener { _, _, _ ->
                finishedSearchBar.setText(searchView.text)
                finishedEventsViewModel.searchFinishedEvents(searchView.text.toString())
                false
            }
        }
    }
}