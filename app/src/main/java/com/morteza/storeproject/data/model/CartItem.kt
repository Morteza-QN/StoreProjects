package com.morteza.storeproject.data.model

data class CartItem(
	val cart_item_id: Int,
	var count: Int,
	val product: Product,
	var changeCountProgressBarIsVisible: Boolean = false
)