package com.morteza.storeproject.feature.favorites

import androidx.lifecycle.MutableLiveData
import com.morteza.storeproject.common.NikeCompletableObserver
import com.morteza.storeproject.common.NikeSingleObserver
import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.data.model.Product
import com.morteza.storeproject.data.repo.product.ProductRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteProductsViewModel(private val productRepository: ProductRepository) : NikeViewModel() {

	val productsLiveData = MutableLiveData<List<Product>>()

	init {
		productRepository.getFavoriteProducts()
			.subscribeOn(Schedulers.io())
			.subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
				override fun onSuccess(t: List<Product>) {
					productsLiveData.postValue(t)
				}
			})
	}

	fun removeFromFavorites(product: Product) {
		productRepository.deleteFromFavorites(product)
			.subscribeOn(Schedulers.io())
			.subscribe(object : NikeCompletableObserver(compositeDisposable) {
				override fun onComplete() {
					Timber.i("removeFromFavorites compeleted")
				}
			})
	}
}