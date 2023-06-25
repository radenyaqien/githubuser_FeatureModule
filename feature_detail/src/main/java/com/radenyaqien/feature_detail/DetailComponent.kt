package com.radenyaqien.feature_detail

import com.radenyaqien.githubuser2023.di.DfmDependencies
import dagger.Component

@Component(dependencies = [DfmDependencies::class])
interface DetailComponent {

    fun inject(fragment: DetailUserFragment)

    @Component.Factory
    interface Factory {
        fun create(dependencies: DfmDependencies): DetailComponent
    }
}