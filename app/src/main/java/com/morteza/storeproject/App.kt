package com.morteza.storeproject

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.morteza.storeproject.data.repo.ProductRepository
import com.morteza.storeproject.data.repo.ProductRepositoryImpl
import com.morteza.storeproject.data.repo.source.ProductLocalDataSource
import com.morteza.storeproject.data.repo.source.ProductRemoteDataSource
import com.morteza.storeproject.feature.main.MainViewModel
import com.morteza.storeproject.services.http.ApiService
import com.morteza.storeproject.services.http.createApiServiceInstance
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber


class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant()
        val myModules = module {
            single<ApiService> { createApiServiceInstance() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            viewModel { MainViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}