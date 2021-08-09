package com.morteza.storeproject

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.multidex.MultiDex
import androidx.room.Room
import com.facebook.drawee.backends.pipeline.Fresco
import com.morteza.storeproject.data.db.AppDataBase
import com.morteza.storeproject.data.repo.banner.BannerRemoteDataSource
import com.morteza.storeproject.data.repo.banner.BannerRepository
import com.morteza.storeproject.data.repo.banner.BannerRepositoryImpl
import com.morteza.storeproject.data.repo.cart.CartRemoteDataSource
import com.morteza.storeproject.data.repo.cart.CartRepository
import com.morteza.storeproject.data.repo.cart.CartRepositoryImpl
import com.morteza.storeproject.data.repo.comment.CommentRemoteDataSource
import com.morteza.storeproject.data.repo.comment.CommentRepository
import com.morteza.storeproject.data.repo.comment.CommentRepositoryImpl
import com.morteza.storeproject.data.repo.order.OrderRemoteDataSource
import com.morteza.storeproject.data.repo.order.OrderRepository
import com.morteza.storeproject.data.repo.order.OrderRepositoryImpl
import com.morteza.storeproject.data.repo.product.ProductRemoteDataSource
import com.morteza.storeproject.data.repo.product.ProductRepository
import com.morteza.storeproject.data.repo.product.ProductRepositoryImpl
import com.morteza.storeproject.data.repo.user.*
import com.morteza.storeproject.feature.auth.AuthViewModel
import com.morteza.storeproject.feature.cart.CartViewModel
import com.morteza.storeproject.feature.checkout.CheckoutViewModel
import com.morteza.storeproject.feature.favorites.FavoriteProductsViewModel
import com.morteza.storeproject.feature.home.HomeViewModel
import com.morteza.storeproject.feature.list.ProductListViewModel
import com.morteza.storeproject.feature.main.MainViewModel
import com.morteza.storeproject.feature.main.ProductListAdapter
import com.morteza.storeproject.feature.order.OrderHistoryViewModel
import com.morteza.storeproject.feature.product.ProductDetailsViewModel
import com.morteza.storeproject.feature.product.comment.CommentListViewModel
import com.morteza.storeproject.feature.profile.ProfileViewModel
import com.morteza.storeproject.feature.shipping.ShippingViewModel
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

			single { Room.databaseBuilder(this@App, AppDataBase::class.java, "db_store").build() }

			factory<ProductRepository> {
				ProductRepositoryImpl(
					ProductRemoteDataSource(get()),
					get<AppDataBase>().getProductDao()
				)
			}
			factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
			factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
			factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }

			factory<ProductListAdapter> { (viewType: Int) -> ProductListAdapter(viewType, get()) }

			single<SharedPreferences> { this@App.getSharedPreferences("app_settings", MODE_PRIVATE) }
			single<UserDataSource> { UserLocalDataSource(get()) }
			single<UserRepository> { UserRepositoryImpl(UserRemoteDataSource(get()), UserLocalDataSource(get())) }
			single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }

			viewModel { HomeViewModel(get(), get()) }
			viewModel { (bundle: Bundle) -> ProductDetailsViewModel(bundle, get(), get()) }
			viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
			viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
			viewModel { AuthViewModel(get()) }
			viewModel { CartViewModel(get()) }
			viewModel { MainViewModel(get()) }
			viewModel { ShippingViewModel(get()) }
			viewModel { (orderId: Int) -> CheckoutViewModel(orderId, get()) }
			viewModel { ProfileViewModel(get()) }
			viewModel { FavoriteProductsViewModel(get()) }
			viewModel { OrderHistoryViewModel(get()) }
		}

		startKoin {
			androidContext(this@App)
			modules(myModules)
		}

		val userRepository: UserRepository = get()
		userRepository.loadToken()
	}
}