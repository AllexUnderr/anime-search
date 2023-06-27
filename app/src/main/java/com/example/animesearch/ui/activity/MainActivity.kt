package com.example.animesearch.ui.activity

import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.animesearch.databinding.ActivityMainBinding
import com.example.animesearch.filter.FilterFragment
import com.example.animesearch.filter.model.AnimeSearchFilters
import com.example.animesearch.search.AnimeFragment

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    private val animeFragment: AnimeFragment by lazy { AnimeFragment() }
    private val filterFragment: FilterFragment by lazy { FilterFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        supportFragmentManager.commit {
            add(binding.fragmentContainer.id, animeFragment)
        }

        setFiltersResultListener()

        setNavigation()
    }

    private fun setFiltersResultListener() {
        supportFragmentManager.setFragmentResultListener(FilterFragment.REQUEST_KEY, this) { _, bundle ->
            BundleCompat.getParcelable(bundle, FilterFragment.BUNDLE_KEY, AnimeSearchFilters::class.java)
                ?.let { filters ->
                    replaceFragment(animeFragment.also { it.setFilters(filters) })
                }
        }
    }

    private fun setNavigation() {
        binding.navigationTopButton.setOnClickListener {
            replaceFragment(animeFragment)
        }

        binding.navigationFiltersButton.setOnClickListener {
            replaceFragment(filterFragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(binding.fragmentContainer.id, fragment)
        }
    }
}