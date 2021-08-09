package com.morteza.storeproject.feature.shipping

import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.data.repo.order.OrderRepository
import com.morteza.storeproject.data.responce.SubmitOrderResponse
import io.reactivex.Single


const val PAYMENT_METHOD_COD = "cash_on_delivery"
const val PAYMENT_METHOD_ONLINE = "online"

class ShippingViewModel(private val orderRepository: OrderRepository) : NikeViewModel() {

	fun postOrder(
		firstName: String, lastName: String, postalCode: String, phoneNumber: String, address: String, paymentMethod: String
	): Single<SubmitOrderResponse> {
		return orderRepository.submit(firstName, lastName, postalCode, phoneNumber, address, paymentMethod)
	}
}
