package com.morteza.storeproject.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.data.Product
import com.morteza.storeproject.data.repo.ProductRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(productRepository: ProductRepository) : NikeViewModel() {

    private val _productLiveData = MutableLiveData<List<Product>>()
    val productLiveData: LiveData<List<Product>> get() = _productLiveData


    init {
        progressBarLiveData.value = true
        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : SingleObserver<List<Product>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<Product>) {
                    _productLiveData.value = t
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }

            })
    }
}