package com.morteza.storeproject.feature.checkout

import androidx.lifecycle.MutableLiveData
import com.morteza.storeproject.common.NikeSingleObserver
import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.common.asyncNetworkRequest
import com.morteza.storeproject.data.model.Checkout
import com.morteza.storeproject.data.repo.order.OrderRepository

class CheckoutViewModel(orderId: Int, orderRepository: OrderRepository) : NikeViewModel() {
	val checkoutLiveData = MutableLiveData<Checkout>()

	init {
		getCheckOut(orderId, orderRepository)
	}

	private fun getCheckOut(orderId: Int, orderRepository: OrderRepository) {
		orderRepository.checkout(orderId)
			.asyncNetworkRequest()
			.subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable) {
				override fun onSuccess(t: Checkout) {
					checkoutLiveData.value = t
				}
			})
	}
}