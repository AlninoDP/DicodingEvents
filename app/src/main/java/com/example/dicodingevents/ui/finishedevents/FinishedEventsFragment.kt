package com.example.dicodingevents.ui.finishedevents

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevents.adapters.EventAdapter
import com.example.dicodingevents.data.Result
import com.example.dicodingevents.databinding.FragmentFinishedEventsBinding
import com.example.dicodingevents.ui.ViewModelFactory

class FinishedEventsFragment : Fragment() {

    private var _binding: FragmentFinishedEventsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val finishedEventViewModel by viewModels<FinishedEventsViewModel> {
            factory
        }

        val eventAdapter = EventAdapter {
            eventEntity ->
            if (eventEntity.isBookmarked){
                finishedEventViewModel.unBookmarkEvent(eventEntity)
            } else {
                finishedEventViewModel.bookmarkEvent(eventEntity)
            }
        }

        finishedEventViewModel.getAllEvents().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbFinishedEvent.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.pbFinishedEvent.visibility = View.GONE
                    finishedEventViewModel.getFinishedEvents()
                        .observe(viewLifecycleOwner) {
                            eventAdapter.submitList(it)
                        }
                    searchEvent(eventAdapter, finishedEventViewModel)
                }

                is Result.Error -> {
                    binding.pbFinishedEvent.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Terjadi kesalahan" + result.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.rvSearchResults.apply {
            binding.rvSearchResults.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvSearchResults.adapter = eventAdapter
        }

        binding.rvFinishedEvents.apply {
            binding.rvFinishedEvents.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvFinishedEvents.adapter = eventAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun searchEvent(eventAdapter: EventAdapter, viewModel: FinishedEventsViewModel) {
        with(binding) {
            searchView.setupWithSearchBar(finishedSearchBar)

            searchView.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    finishedSearchBar.setText(searchView.text)
                    viewModel.searchEvent("%${char.toString()}%", 1)
                        .observe(viewLifecycleOwner) {
                            eventAdapter.submitList(it)
                        }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            searchView.editText.setOnEditorActionListener { _, _, _ ->
                finishedSearchBar.setText(searchView.text)
                viewModel.searchEvent(searchView.text.toString())
                false
            }
        }
    }
}