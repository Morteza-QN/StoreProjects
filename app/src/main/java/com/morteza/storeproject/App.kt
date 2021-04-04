package com.morteza.storeproject

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

interface HttpClient {
    fun sendRequest()
}

class Retrofit : HttpClient {
    override fun sendRequest() {
        TODO("Not yet implemented")
    }
}

interface ImageLoadingService {
    fun load(imageUrl: String)
}

class Picasso : ImageLoadingService {
    override fun load(imageUrl: String) {
        TODO("Not yet implemented")
    }
}

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val myModules = module {
            factory<HttpClient> { Retrofit() }
            factory<ImageLoadingService> { Picasso() }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}