package com.example.shoppingproto.di

import com.example.shoppingproto.ui.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }
}
