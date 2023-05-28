package com.example.animesearch.component

import android.content.Context
import com.example.animesearch.filter.FilterDialogFragment
import com.example.animesearch.search.AnimeFragment
import com.example.animesearch.module.AnimeModule
import com.example.animesearch.module.FilterModule
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
    modules = [
        AppComponent.Companion.AppModule::class,
        AnimeModule::class,
        FilterModule::class,
    ]
)
interface AppComponent {

    fun inject(animeListFragment: AnimeFragment)

    fun inject(filterDialogFragment: FilterDialogFragment)

    companion object {
        @Module
        class AppModule(private val context: Context) {
            @Provides
            fun provideContext(): Context = context
        }
    }
}