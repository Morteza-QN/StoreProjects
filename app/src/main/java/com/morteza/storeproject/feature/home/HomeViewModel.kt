package com.morteza.storeproject.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.morteza.storeproject.common.NikeSingleObserver
import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.data.Banner
import com.morteza.storeproject.data.Product
import com.morteza.storeproject.data.SORT_LATEST
import com.morteza.storeproject.data.repo.banner.BannerRepository
import com.morteza.storeproject.data.repo.product.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(productRepository: ProductRepository, bannerRepository: BannerRepository) : NikeViewModel() {

	private val _productLiveData = MutableLiveData<List<Product>>()
	val productLiveData: LiveData<List<Product>> get() = _productLiveData
	val bannerLiveData = MutableLiveData<List<Banner>>()

	init {
		getProducts(productRepository)
		getBanners(bannerRepository)
	}

	private fun getBanners(bannerRepository: BannerRepository) {
		bannerRepository.getBanner()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
				override fun onSuccess(t: List<Banner>) {
					bannerLiveData.value = t
				}
			})
	}

	private fun getProducts(productRepository: ProductRepository) {
		progressBarLiveData.value = true
		productRepository.getProducts(SORT_LATEST)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.doFinally { progressBarLiveData.value = false }
			.subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
				override fun onSuccess(t: List<Product>) {
					_productLiveData.value = t
				}
			})
	}
}