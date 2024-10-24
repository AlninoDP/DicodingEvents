package com.example.dicodingevents.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevents.CarouselAdapter
import com.example.dicodingevents.EventListAdapter
import com.example.dicodingevents.data.remote.response.ListEventsItem
import com.example.dicodingevents.databinding.FragmentHomeBinding
import com.example.dicodingevents.ui.ViewModelFactory
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
    private val homeViewModel by viewModels<HomeViewModel>{
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val pastEventLayoutManager = LinearLayoutManager(requireActivity())
        binding.rvPastEvents.layoutManager = pastEventLayoutManager


        binding.carousel.layoutManager = CarouselLayoutManager()
        CarouselSnapHelper().attachToRecyclerView(binding.carousel)

        //observer
        homeViewModel.listFinishedEventsItem.observe(viewLifecycleOwner){
            setEventsData(it)
        }
        homeViewModel.listUpcomingEventsItem.observe(viewLifecycleOwner){
            setCarouselData(it)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        homeViewModel.snackBarText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    binding.rvPastEvents,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setEventsData(events: List<ListEventsItem>) {
        val eventListAdapter = EventListAdapter(events)
        binding.rvPastEvents.adapter = eventListAdapter
    }
    private fun setCarouselData(events: List<ListEventsItem>) {
        val carouselAdapter = CarouselAdapter(events)
        binding.carousel.adapter = carouselAdapter
    }

    /// Show progress bar
    private fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            binding.progressBarHome1.visibility = View.VISIBLE
            binding.progressBarHome2.visibility = View.VISIBLE
        } else {
            binding.progressBarHome1.visibility = View.INVISIBLE
            binding.progressBarHome2.visibility = View.INVISIBLE
        }
    }
}