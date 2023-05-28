package com.example.animesearch.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.animesearch.databinding.FragmentAnimeBinding
import com.example.animesearch.filter.FilterDialogFragment
import com.example.animesearch.filter.model.Filter
import com.example.animesearch.helper.MainApplication
import javax.inject.Inject

class AnimeFragment : Fragment(), FilterDialogFragment.Listener {

    private var _binding: FragmentAnimeBinding? = null
    private val binding get() = _binding!!

    private val animeAdapter = AnimeRecyclerAdapter()

    @Inject
    lateinit var viewModel: AnimeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAnimeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onFiltersPicked(filters: List<Filter>) {
        viewModel.loadFilteredAnimes(filters)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.destroy()
    }

    private fun initViews() {
        binding.showTopAnimeButton.setOnClickListener {
            viewModel.loadTopAnime()
        }

        binding.showFilterDialogButton.setOnClickListener {
            FilterDialogFragment().show(childFragmentManager, FilterDialogFragment.TAG)
        }

        binding.animeRecyclerView.adapter = animeAdapter
    }

    private fun bindViewModel() {
        viewModel.animes.observe(viewLifecycleOwner) {
            animeAdapter.submitList(it)
        }
    }
}