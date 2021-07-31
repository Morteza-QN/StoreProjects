package com.morteza.storeproject.data.repo.cart

import com.morteza.storeproject.data.CartItemCount
import com.morteza.storeproject.data.repo.source.cart.CartDataSource
import com.morteza.storeproject.data.responce.AddToCartResponse
import com.morteza.storeproject.data.responce.CartResponse
import com.morteza.storeproject.data.responce.MessageResponse
import io.reactivex.Single

class CartRepositoryImpl(private val cartRemoteDataSource: CartDataSource) : CartRepository {
	override fun addToCart(productId: Int): Single<AddToCartResponse> {
		return cartRemoteDataSource.addToCart(productId)
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