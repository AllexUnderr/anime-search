package com.example.animesearch.animeList

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.animesearch.RetrofitBuilder
import com.example.animesearch.databinding.FragmentSearchBinding
import com.example.animesearch.search.AnimeApi
import com.example.animesearch.search.AnimeRecyclerAdapter
import com.example.animesearch.search.model.AnimeStatus
import com.example.animesearch.search.model.AnimeType

class AnimeListFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val search = RetrofitBuilder.retrofit.create(AnimeApi::class.java)
        val animeCall = search.animesByFilters(
            type = AnimeType.TV,
            status = AnimeStatus.COMPLETE,
            minScore = 8.0,
            genres = "4,22"
        )

        val animeAdapter = AnimeRecyclerAdapter()
        binding.animeRecyclerView.adapter = animeAdapter
        val handler = Handler(Looper.getMainLooper())
        Thread {
            animeCall.execute().body()?.let {
                handler.post { animeAdapter.submitList(it.data) }
            }
        }.start()
    }
}