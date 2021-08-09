package com.morteza.storeproject.data.model

data class OrderItem(
	val count: Int,
	val discount: Int,
	val id: Int,
	val order_id: Int,
	val price: Int,
	val product: Product,
	val product_id: Int
)