package com.morteza.storeproject.data.repo.order

import com.morteza.storeproject.data.model.Checkout
import com.morteza.storeproject.data.model.OrderHistoryItem
import com.morteza.storeproject.data.responce.SubmitOrderResponse
import io.reactivex.Single

class OrderRepositoryImpl(private val orderDataSource: OrderDataSource) : OrderRepository {
	override fun submit(
		firstName: String,
		lastName: String,
		postalCode: String,
		phoneNumber: String,
		address: String,
		paymentMethod: String
	): Single<SubmitOrderResponse> {
		return orderDataSource.submit(
			firstName,
			lastName,
			postalCode,
			phoneNumber,
			address,
			paymentMethod
		)
	}

	override fun checkout(orderId: Int): Single<Checkout> = orderDataSource.checkout(orderId)

	override fun list(): Single<List<OrderHistoryItem>> = orderDataSource.list()
}