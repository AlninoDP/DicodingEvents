package com.example.dicodingevents.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevents.adapters.CarouselAdapter
import com.example.dicodingevents.adapters.EventListAdapter
import com.example.dicodingevents.data.Result
import com.example.dicodingevents.databinding.FragmentHomeBinding
import com.example.dicodingevents.ui.ViewModelFactory
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())

        val homeViewModel by viewModels<HomeViewModel> {
            factory
        }

        val carouselAdapter = CarouselAdapter()
        val eventListAdapter = EventListAdapter{
                eventEntity ->
            if (eventEntity.isBookmarked){
                homeViewModel.unBookmarkEvent(eventEntity)
            } else {
                homeViewModel.bookmarkEvent(eventEntity)
            }
        }

        homeViewModel.getAllEvents().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBarHome1.visibility = View.VISIBLE
                        binding.progressBarHome2.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBarHome1.visibility = View.GONE
                        binding.progressBarHome2.visibility = View.GONE
                        homeViewModel.getUpcomingEvents().observe(viewLifecycleOwner) {
                            carouselAdapter.submitList(it)
                        }
                        homeViewModel.getFinishedEvents().observe(viewLifecycleOwner) {
                            eventListAdapter.submitList(it)
                        }

                    }

                    is Result.Error -> {
                        binding.progressBarHome1.visibility = View.GONE
                        binding.progressBarHome2.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }

        binding.rvPastEvents.apply {
            binding.rvPastEvents.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvPastEvents.adapter = eventListAdapter
        }


        binding.carousel.apply {
            binding.carousel.layoutManager = CarouselLayoutManager()
            binding.carousel.adapter = carouselAdapter

        }
        binding.carousel.itemAnimator = null
        CarouselSnapHelper().attachToRecyclerView(binding.carousel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}