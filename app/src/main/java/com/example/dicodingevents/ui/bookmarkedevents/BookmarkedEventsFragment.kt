package com.example.dicodingevents.ui.bookmarkedevents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevents.adapters.EventAdapter
import com.example.dicodingevents.databinding.FragmentBookmarkedEventsBinding
import com.example.dicodingevents.ui.ViewModelFactory

class BookmarkedEventsFragment : Fragment() {

    private var _binding: FragmentBookmarkedEventsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        _binding = FragmentBookmarkedEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val bookmarkedEventsViewModel by viewModels<BookmarkedEventsViewModel> {
            factory
        }


        val eventAdapter = EventAdapter {
                eventEntity ->
            if (eventEntity.isBookmarked){
                bookmarkedEventsViewModel.unBookmarkEvent(eventEntity)
            } else {
                bookmarkedEventsViewModel.bookmarkEvent(eventEntity)
            }
        }

        bookmarkedEventsViewModel.getBookmarkedEvents()
            .observe(viewLifecycleOwner) { bookmarkedEvents ->
                eventAdapter.submitList(bookmarkedEvents)
            }

        binding.rvBookmarkedEvents.apply {
            binding.rvBookmarkedEvents.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvBookmarkedEvents.adapter = eventAdapter
        }

    }

}