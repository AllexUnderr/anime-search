package com.example.animesearch.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.animesearch.R
import com.example.animesearch.databinding.FragmentFiltersBinding
import com.example.animesearch.filter.model.Filter
import com.example.animesearch.filter.model.MinScore
import com.example.animesearch.helper.MainApplication
import com.example.animesearch.search.model.AnimeStatus
import com.example.animesearch.search.model.AnimeType
import com.example.animesearch.search.model.OrderBy
import javax.inject.Inject

class FilterDialogFragment : DialogFragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: FilterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViewModel()
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.destroy()
    }

    private fun initViews() {
        binding.confirmButton.setOnClickListener {
            setFilters()
            dismiss()
        }
    }

    private fun bindViewModel() {
        val genreAdapter = GenreRecyclerAdapter(viewModel.genreCheckListener)
        binding.genresRecyclerView.adapter = genreAdapter

        viewModel.genres.observe(this) {
            genreAdapter.submitList(it)
        }

        viewModel.passFiltersCommand.observe(this) {
            (parentFragment as Listener)
                .onFiltersPicked(it)
        }

        viewModel.loadGenres()
    }


    private fun setFilters() {
        viewModel.passFilters(
            getAnimeType(),
            getMinScore(),
            viewModel.checkedGenres,
            getAnimeStatus(),
            getOrderBy()
        )
    }

    private fun getAnimeType() =
        when (binding.animeTypeRadioGroup.checkedRadioButtonId) {
            R.id.tvRadioButton -> AnimeType.TV
            R.id.movieRadioButton -> AnimeType.MOVIE
            R.id.ovaRadioButton -> AnimeType.OVA
            R.id.specialRadioButton -> AnimeType.SPECIAL
            R.id.onaRadioButton -> AnimeType.ONA
            R.id.musicRadioButton -> AnimeType.MUSIC
            else -> AnimeType.TV
        }

    private fun getMinScore() =
        MinScore(
            if (binding.editTextNumberSigned.text.isNotBlank())
                binding.editTextNumberSigned.text.toString().toDouble()
            else
                0.0
        )

    private fun getAnimeStatus() =
        when (binding.animeStatusRadioGroup.checkedRadioButtonId) {
            R.id.completeRadioButton -> AnimeStatus.COMPLETE
            R.id.airingRadioButton -> AnimeStatus.AIRING
            R.id.upcoming -> AnimeStatus.UPCOMING
            else -> AnimeStatus.COMPLETE
        }

    private fun getOrderBy() =
        when (binding.orderByRadioGroup.checkedRadioButtonId) {
            R.id.rankRadioButton -> OrderBy.RANK
            R.id.popularityRadioButton -> OrderBy.POPULARITY
            R.id.scoreRadioButton -> OrderBy.SCORE
            R.id.endDateRadioButton -> OrderBy.END_DATE
            else -> OrderBy.RANK
        }

    interface Listener {
        fun onFiltersPicked(filters: List<Filter>)
    }

    companion object {
        const val TAG = "FilterDialogFragment"
    }
}