package com.example.animesearch.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.animesearch.R
import com.example.animesearch.databinding.FragmentFiltersBinding
import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.search.model.AnimeStatus
import com.example.animesearch.search.model.AnimeType
import com.example.animesearch.search.model.OrderBy
import android.R.layout.simple_spinner_item
import android.R.layout.simple_spinner_dropdown_item
import com.example.animesearch.search.model.getStringResourceId
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterDialogFragment : DialogFragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = requireNotNull(_binding)

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

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        val animeTypes = listOf(getString(R.string.no_matter)) + AnimeType.values().map { getString(it.getStringResourceId()) }
        ArrayAdapter(requireContext(), simple_spinner_item, animeTypes).also {
            it.setDropDownViewResource(simple_spinner_dropdown_item)
            binding.typeSpinner.adapter = it
        }

        val animeStatuses = listOf(getString(R.string.no_matter)) + AnimeStatus.values().map { getString(it.getStringResourceId()) }
        ArrayAdapter(requireContext(), simple_spinner_item, animeStatuses).also {
            it.setDropDownViewResource(simple_spinner_dropdown_item)
            binding.statusSpinner.adapter = it
        }

        val orderBy = listOf(getString(R.string.no_matter)) + OrderBy.values().map { getString(it.getStringResourceId()) }
        ArrayAdapter(requireContext(), simple_spinner_item, orderBy).also {
            it.setDropDownViewResource(simple_spinner_dropdown_item)
            binding.orderBySpinner.adapter = it
        }

        binding.confirmButton.setOnClickListener {
            onConfirmButtonClick()
            dismiss()
        }
    }

    private fun bindViewModel() {
        val genreAdapter = GenreRecyclerAdapter()
        binding.genresRecyclerView.adapter = genreAdapter
        viewModel.genreList.observe(this) {
            genreAdapter.submitList(it)
        }

        viewModel.passFiltersCommand.observe(this) {
            (parentFragment as Listener)
                .onFiltersPicked(it)
        }
    }

    private fun onConfirmButtonClick() {
        viewModel.onConfirmButtonClick(
            AnimeSearchFilters(
                type = getAnimeType(),
                minScore = getMinScore(),
                genres = viewModel.getCheckedGenres(),
                status = getAnimeStatus(),
                orderBy = getOrderBy(),
            )
        )
    }

    private fun getAnimeType(): AnimeType? =
        if (binding.typeSpinner.selectedItemPosition != 0)
            AnimeType.values()[binding.typeSpinner.selectedItemPosition - 1]
        else
            null

    private fun getMinScore() =
        if (!binding.editTextNumberSigned.text.isNullOrBlank())
            binding.editTextNumberSigned.text.toString().toDouble()
        else
            null

    private fun getAnimeStatus(): AnimeStatus? =
        if (binding.statusSpinner.selectedItemPosition != 0)
            AnimeStatus.values()[binding.statusSpinner.selectedItemPosition - 1]
        else
            null

    private fun getOrderBy(): OrderBy? =
        if (binding.orderBySpinner.selectedItemPosition != 0)
            OrderBy.values()[binding.orderBySpinner.selectedItemPosition - 1]
        else
            null


    interface Listener {
        fun onFiltersPicked(filters: AnimeSearchFilters)
    }

    companion object {
        const val TAG = "FilterDialogFragment"
    }
}