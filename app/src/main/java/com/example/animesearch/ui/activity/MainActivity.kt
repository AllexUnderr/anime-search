package com.example.animesearch.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.animesearch.databinding.ActivityMainBinding
import com.example.animesearch.search.AnimeFragment

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        supportFragmentManager.commit {
            add(binding.fragmentContainer.id, AnimeFragment())
        }
    }
}