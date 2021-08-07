package com.morteza.storeproject.data.repo.cart

import com.morteza.storeproject.data.CartItemCount
import com.morteza.storeproject.data.repo.source.cart.CartDataSource
import com.morteza.storeproject.data.responce.AddToCartResponse
import com.morteza.storeproject.data.responce.CartResponse
import com.morteza.storeproject.data.responce.MessageResponse
import io.reactivex.Single

class CartRepositoryImpl(private val cartRemote: CartDataSource) : CartRepository {

	override fun addToCart(productId: Int): Single<AddToCartResponse> = cartRemote.addToCart(productId)
	override fun get(): Single<CartResponse> = cartRemote.get()
	override fun remove(cartItemId: Int): Single<MessageResponse> = cartRemote.remove(cartItemId)
	override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> =
		cartRemote.changeCount(cartItemId, count)

	override fun getCartItemCount(): Single<CartItemCount> = cartRemote.getCartItemCount()
}