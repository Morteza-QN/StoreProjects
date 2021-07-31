package com.morteza.storeproject.data.repo.source.cart

import com.google.gson.JsonObject
import com.morteza.storeproject.data.CartItemCount
import com.morteza.storeproject.data.responce.AddToCartResponse
import com.morteza.storeproject.data.responce.CartResponse
import com.morteza.storeproject.data.responce.MessageResponse
import com.morteza.storeproject.services.http.ApiService
import io.reactivex.Single

private const val KEY_PRODUCT_ID = "product_id"

class CartRemoteDataSource(private val apiService: ApiService) : CartDataSource {
	override fun addToCart(productId: Int): Single<AddToCartResponse> {
		return apiService.addToCart(JsonObject().apply { addProperty(KEY_PRODUCT_ID, productId) })
	}

	override fun get(): Single<CartResponse> {
		TODO("Not yet implemented")
	}

	override fun remove(cartItemId: Int): Single<MessageResponse> {
		TODO("Not yet implemented")
	}

	override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
		TODO("Not yet implemented")
	}

	override fun getCartItemCount(): Single<CartItemCount> {
		TODO("Not yet implemented")
	}
}