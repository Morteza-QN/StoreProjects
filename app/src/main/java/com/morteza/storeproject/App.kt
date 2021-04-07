package com.morteza.storeproject

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.facebook.drawee.backends.pipeline.Fresco
import com.morteza.storeproject.data.repo.BannerRepository
import com.morteza.storeproject.data.repo.BannerRepositoryImpl
import com.morteza.storeproject.data.repo.ProductRepository
import com.morteza.storeproject.data.repo.ProductRepositoryImpl
import com.morteza.storeproject.data.repo.source.banner.BannerRemoteDataSource
import com.morteza.storeproject.data.repo.source.product.ProductLocalDataSource
import com.morteza.storeproject.data.repo.source.product.ProductRemoteDataSource
import com.morteza.storeproject.feature.main.MainViewModel
import com.morteza.storeproject.feature.main.ProductListAdapter
import com.morteza.storeproject.services.http.ApiService
import com.morteza.storeproject.services.http.createApiServiceInstance
import com.morteza.storeproject.services.imageLoading.FrescoImageLoadingService
import com.morteza.storeproject.services.imageLoading.ImageLoadingService
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

		Timber.plant(Timber.DebugTree())

		Fresco.initialize(this)

		val myModules = module {
			single<ApiService> { createApiServiceInstance() }
			single<ImageLoadingService> { FrescoImageLoadingService() }
			factory<ProductRepository> {
				ProductRepositoryImpl(
					ProductRemoteDataSource(get()),
					ProductLocalDataSource()
				)
			}
			factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
			factory<ProductListAdapter> { ProductListAdapter(get()) }
			viewModel { MainViewModel(get(), get()) }
		}

		startKoin {
			androidContext(this@App)
			modules(myModules)
		}
	}
}