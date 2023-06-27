package com.example.animesearch.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.animesearch.databinding.FragmentFiltersBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterFragment : Fragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding: FragmentFiltersBinding
        get() = requireNotNull(_binding)

    private val viewModel: FilterViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViewModel()

        viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.confirmButton.setOnClickListener {
            viewModel.onConfirmButton()
        }

        binding.minScoreEditText.addTextChangedListener {
            viewModel.onMinScoreChanged(it.toString())
        }
    }

    private fun bindViewModel() {
        val genreAdapter = GenreRecyclerAdapter()
        binding.genresRecyclerView.adapter = genreAdapter
        viewModel.genreList.observe(viewLifecycleOwner) {
            genreAdapter.submitList(it)
        }

        val typeAdapter = ChipRecyclerAdapter(viewModel::onTypeClick)
        binding.typeRecyclerView.adapter = typeAdapter
        viewModel.typeChips.observe(viewLifecycleOwner) {
            typeAdapter.submitList(it)
        }

        val statusAdapter = ChipRecyclerAdapter(viewModel::onStatusClick)
        binding.statusRecyclerView.adapter = statusAdapter
        viewModel.statusChips.observe(viewLifecycleOwner) {
            statusAdapter.submitList(it)
        }

        val orderByAdapter = ChipRecyclerAdapter(viewModel::onOrderByClick)
        binding.orderByRecyclerView.adapter = orderByAdapter
        viewModel.orderByChips.observe(viewLifecycleOwner) {
            orderByAdapter.submitList(it)
        }

        viewModel.openAnimesCommand.observe(viewLifecycleOwner) {
            setFragmentResult(
                REQUEST_KEY,
                bundleOf(BUNDLE_KEY to it)
            )
        }
    }

    companion object {
        const val REQUEST_KEY = "filters"

        const val BUNDLE_KEY = "AnimeSearchFilters"
    }
}