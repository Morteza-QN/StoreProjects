package com.morteza.storeproject.data.repo.order

import com.google.gson.JsonObject
import com.morteza.storeproject.data.model.Checkout
import com.morteza.storeproject.data.model.OrderHistoryItem
import com.morteza.storeproject.data.responce.SubmitOrderResponse
import com.morteza.storeproject.services.http.ApiService
import io.reactivex.Single

class OrderRemoteDataSource(private val apiService: ApiService) : OrderDataSource {
	override fun submit(
		firstName: String,
		lastName: String,
		postalCode: String,
		phoneNumber: String,
		address: String,
		paymentMethod: String
	): Single<SubmitOrderResponse> {
		return apiService.submitOrder(JsonObject().apply {
			addProperty("first_name", firstName)
			addProperty("last_name", lastName)
			addProperty("postal_code", postalCode)
			addProperty("mobile", phoneNumber)
			addProperty("address", address)
			addProperty("payment_method", paymentMethod)
		})
	}

	override fun checkout(orderId: Int): Single<Checkout> = apiService.checkout(orderId)

	override fun list(): Single<List<OrderHistoryItem>> = apiService.orders()
}