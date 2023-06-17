package com.example.animesearch.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.animesearch.R
import com.example.animesearch.databinding.FragmentAnimeBinding
import com.example.animesearch.filter.model.AnimeSearchFilters
import com.google.android.material.button.MaterialButton
import com.kennyc.view.MultiStateView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimeFragment : Fragment() {

    private var _binding: FragmentAnimeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: AnimeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAnimeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setFilters(filters: AnimeSearchFilters) {
        viewModel.onFiltersApplied(filters)
    }

    private fun bindViewModel() {
        val animeAdapter = AnimePagingAdapter()
        binding.animeRecyclerView.adapter = animeAdapter

        viewModel.scrollToCommand.observe(viewLifecycleOwner) {
            binding.animeRecyclerView.scrollToPosition(it)
        }

        setRetryButtonClickListener(animeAdapter)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                updateViewOnStateChange(animeAdapter)

                viewModel.animeFlow.collectLatest {
                    animeAdapter.submitData(it)
                }

            }
        }
    }


    private fun setRetryButtonClickListener(animeAdapter: AnimePagingAdapter) {
        val errorView = binding.animeMultiStateView.getView(MultiStateView.ViewState.ERROR)
        errorView?.findViewById<MaterialButton>(R.id.retryButton)
            ?.let {
                it.setOnClickListener {
                    animeAdapter.retry()
                }
            }
    }

    private fun updateViewOnStateChange(animeAdapter: AnimePagingAdapter) {
        animeAdapter.addLoadStateListener {
            binding.animeMultiStateView.viewState = when {
                it.prepend is LoadState.NotLoading && it.prepend.endOfPaginationReached -> MultiStateView.ViewState.CONTENT
                it.refresh is LoadState.Error -> MultiStateView.ViewState.ERROR
                !it.prepend.endOfPaginationReached -> MultiStateView.ViewState.LOADING
                else -> MultiStateView.ViewState.EMPTY
            }
        }
    }
}