package com.example.shoppingproto

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.shoppingproto.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShoppingMainApp:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShoppingMainApp)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }
}