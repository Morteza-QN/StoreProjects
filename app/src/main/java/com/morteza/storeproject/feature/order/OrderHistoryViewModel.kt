package com.morteza.storeproject.feature.order

import androidx.lifecycle.MutableLiveData
import com.morteza.storeproject.common.NikeSingleObserver
import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.common.asyncNetworkRequest
import com.morteza.storeproject.data.model.OrderHistoryItem
import com.morteza.storeproject.data.repo.order.OrderRepository

class OrderHistoryViewModel(orderRepository: OrderRepository) : NikeViewModel() {

	val orders = MutableLiveData<List<OrderHistoryItem>>()

	init {
		progressBarLiveData.value = true
		orderRepository.list()
			.asyncNetworkRequest()
			.doFinally { progressBarLiveData.value = false }
			.subscribe(object : NikeSingleObserver<List<OrderHistoryItem>>(compositeDisposable) {
				override fun onSuccess(t: List<OrderHistoryItem>) {
					orders.value = t
				}
			})
	}
}