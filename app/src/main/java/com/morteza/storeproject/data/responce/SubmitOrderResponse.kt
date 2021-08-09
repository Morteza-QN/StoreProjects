package com.morteza.storeproject.data.responce

data class SubmitOrderResponse(
	val bank_gateway_url: String,
	val order_id: Int
)