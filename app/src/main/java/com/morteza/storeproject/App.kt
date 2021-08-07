package com.morteza.storeproject

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.multidex.MultiDex
import com.facebook.drawee.backends.pipeline.Fresco
import com.morteza.storeproject.data.repo.banner.BannerRepository
import com.morteza.storeproject.data.repo.banner.BannerRepositoryImpl
import com.morteza.storeproject.data.repo.cart.CartRepository
import com.morteza.storeproject.data.repo.cart.CartRepositoryImpl
import com.morteza.storeproject.data.repo.comment.CommentRepository
import com.morteza.storeproject.data.repo.comment.CommentRepositoryImpl
import com.morteza.storeproject.data.repo.product.ProductRepository
import com.morteza.storeproject.data.repo.product.ProductRepositoryImpl
import com.morteza.storeproject.data.repo.source.banner.BannerRemoteDataSource
import com.morteza.storeproject.data.repo.source.cart.CartRemoteDataSource
import com.morteza.storeproject.data.repo.source.comment.CommentRemoteDataSource
import com.morteza.storeproject.data.repo.source.product.ProductLocalDataSource
import com.morteza.storeproject.data.repo.source.product.ProductRemoteDataSource
import com.morteza.storeproject.data.repo.source.user.UserLocalDataSource
import com.morteza.storeproject.data.repo.source.user.UserRemoteDataSource
import com.morteza.storeproject.data.repo.user.UserRepository
import com.morteza.storeproject.data.repo.user.UserRepositoryImpl
import com.morteza.storeproject.feature.auth.AuthViewModel
import com.morteza.storeproject.feature.cart.CartViewModel
import com.morteza.storeproject.feature.home.HomeViewModel
import com.morteza.storeproject.feature.list.ProductListViewModel
import com.morteza.storeproject.feature.main.MainViewModel
import com.morteza.storeproject.feature.main.ProductListAdapter
import com.morteza.storeproject.feature.product.ProductDetailsViewModel
import com.morteza.storeproject.feature.product.comment.CommentListViewModel
import com.morteza.storeproject.services.http.ApiService
import com.morteza.storeproject.services.http.createApiServiceInstance
import com.morteza.storeproject.services.imageLoading.FrescoImageLoadingService
import com.morteza.storeproject.services.imageLoading.ImageLoadingService
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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
				ProductRepositoryImpl(ProductRemoteDataSource(get()), ProductLocalDataSource())
			}
			factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
			factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
			factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }
			factory<ProductListAdapter> { (viewType: Int) -> ProductListAdapter(viewType, get()) }

			single<SharedPreferences> { this@App.getSharedPreferences("app_settings", MODE_PRIVATE) }
			single<UserRepository> { UserRepositoryImpl(UserRemoteDataSource(get()), UserLocalDataSource(get())) }

			single { UserLocalDataSource(get()) }

			viewModel { HomeViewModel(get(), get()) }
			viewModel { (bundle: Bundle) -> ProductDetailsViewModel(bundle, get(), get()) }
			viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
			viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
			viewModel { AuthViewModel(get()) }
			viewModel { CartViewModel(get()) }
			viewModel { MainViewModel(get()) }
		}

		startKoin {
			androidContext(this@App)
			modules(myModules)
		}

		val userRepository: UserRepository = get()
		userRepository.loadToken()
	}
}