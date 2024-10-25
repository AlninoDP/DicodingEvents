package com.example.dicodingevents.ui.upcomingevents

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevents.EventAdapter
import com.example.dicodingevents.data.Result
import com.example.dicodingevents.data.remote.response.ListEventsItem
import com.example.dicodingevents.databinding.FragmentUpcomingEventsBinding
import com.example.dicodingevents.ui.ViewModelFactory
import com.example.dicodingevents.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class UpcomingEventsFragment : Fragment() {

    private var _binding: FragmentUpcomingEventsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val upcomingEventViewModel by viewModels<UpcomingEventsViewModel> {
            factory
        }
        val eventAdapter = EventAdapter()

        upcomingEventViewModel.getAllEvents().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    upcomingEventViewModel.getUpcomingEvents()
                        .observe(viewLifecycleOwner) {
                            eventAdapter.submitList(it)
                        }
                    searchEvent(eventAdapter, upcomingEventViewModel)
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Terjadi kesalahan" + result.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.rvSearchResultsUpcoming.apply {
            binding.rvSearchResultsUpcoming.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvSearchResultsUpcoming.adapter = eventAdapter
        }

        binding.rvUpcomingEvents.apply {
            binding.rvUpcomingEvents.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvUpcomingEvents.adapter = eventAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchEvent(eventAdapter: EventAdapter, viewModel: UpcomingEventsViewModel) {
        with(binding) {
            searchViewUpcoming.setupWithSearchBar(upcomingSearchBar)

            searchViewUpcoming.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    upcomingSearchBar.setText(searchViewUpcoming.text)
                    viewModel.searchEvent("%${char.toString()}%", 0)
                        .observe(viewLifecycleOwner) {
                            eventAdapter.submitList(it)
                        }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            searchViewUpcoming.editText.setOnEditorActionListener { _, _, _ ->
                upcomingSearchBar.setText(searchViewUpcoming.text)
                viewModel.searchEvent(searchViewUpcoming.text.toString())
                false
            }
        }
    }

}