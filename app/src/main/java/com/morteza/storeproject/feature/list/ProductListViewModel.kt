package com.morteza.storeproject.feature.list

import androidx.lifecycle.MutableLiveData
import com.morteza.storeproject.R
import com.morteza.storeproject.common.NikeSingleObserver
import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.common.asyncNetworkRequest
import com.morteza.storeproject.data.model.Product
import com.morteza.storeproject.data.repo.product.ProductRepository

class ProductListViewModel(
	private var sort: Int,
	private val productRepository: ProductRepository
) : NikeViewModel() {

	val productLiveData = MutableLiveData<List<Product>>()
	val selectedSortTitleLiveData = MutableLiveData<Int>()
	private val sortTitles = arrayOf(
		R.string.sortLatest,
		R.string.sortPopular,
		R.string.sortPriceHighToLow,
		R.string.sortPriceLowToHigh
	)

	init {
		updateList(sort)
	}

	fun updateList(sort: Int) {
		this.sort = sort
		getProducts()
		sortTitleChanged(sort)
	}

	fun getSelectedSort(): Int = sort

	private fun sortTitleChanged(sort: Int) {
		selectedSortTitleLiveData.value = sortTitles[sort]
	}

	private fun getProducts() {
		progressBarLiveData.value = true
		productRepository.getProducts(sort)
			.asyncNetworkRequest()
			.doFinally { progressBarLiveData.value = false }
			.subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
				override fun onSuccess(data: List<Product>) {
					productLiveData.value = data
				}
			})
	}
}