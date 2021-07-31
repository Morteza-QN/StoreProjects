package com.morteza.storeproject.data.repo.source.cart

import com.morteza.storeproject.data.CartItemCount
import com.morteza.storeproject.data.responce.AddToCartResponse
import com.morteza.storeproject.data.responce.CartResponse
import com.morteza.storeproject.data.responce.MessageResponse
import io.reactivex.Single

interface CartDataSource {

	fun addToCart(productId: Int): Single<AddToCartResponse>
	fun get(): Single<CartResponse>
	fun remove(cartItemId: Int): Single<MessageResponse>
	fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse>
	fun getCartItemCount(): Single<CartItemCount>
}