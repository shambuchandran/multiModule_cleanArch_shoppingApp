package com.example.shoppingproto.di

import org.koin.dsl.module

val presentationModule = module {
    includes(viewModelModule)
}