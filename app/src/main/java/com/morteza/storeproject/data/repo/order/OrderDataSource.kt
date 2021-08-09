package com.morteza.storeproject.data.repo.order

import com.morteza.storeproject.data.model.Checkout
import com.morteza.storeproject.data.model.OrderHistoryItem
import com.morteza.storeproject.data.responce.SubmitOrderResponse
import io.reactivex.Single

interface OrderDataSource {

	fun submit(
		firstName: String,
		lastName: String,
		postalCode: String,
		phoneNumber: String,
		address: String,
		paymentMethod: String
	): Single<SubmitOrderResponse>

	fun checkout(orderId: Int): Single<Checkout>

	fun list(): Single<List<OrderHistoryItem>>
}