package com.morteza.storeproject.data.repo.cart

import com.google.gson.JsonObject
import com.morteza.storeproject.data.model.CartItemCount
import com.morteza.storeproject.data.responce.AddToCartResponse
import com.morteza.storeproject.data.responce.CartResponse
import com.morteza.storeproject.data.responce.MessageResponse
import com.morteza.storeproject.services.http.ApiService
import io.reactivex.Single

private const val KEY_PRODUCT_ID = "product_id"
private const val KEY_CART_ITEM_ID = "cart_item_id"
private const val KEY_COUNT = "count"

class CartRemoteDataSource(private val apiService: ApiService) : CartDataSource {
	override fun addToCart(productId: Int): Single<AddToCartResponse> {
		return apiService.addToCart(JsonObject().apply { addProperty(KEY_PRODUCT_ID, productId) })
	}

	override fun get(): Single<CartResponse> {
		return apiService.getCart()
	}

	override fun remove(cartItemId: Int): Single<MessageResponse> {
		return apiService.removeItemFromCart(JsonObject().apply { addProperty(KEY_CART_ITEM_ID, cartItemId) })
	}

	override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
		return apiService.changeCount(JsonObject().apply {
			addProperty(KEY_CART_ITEM_ID, cartItemId)
			addProperty(KEY_COUNT, count)
		})
	}

	override fun getCartItemCount(): Single<CartItemCount> {
		return apiService.getCartItemsCount()
	}
}