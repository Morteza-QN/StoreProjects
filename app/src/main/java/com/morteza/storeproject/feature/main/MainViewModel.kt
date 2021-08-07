package com.morteza.storeproject.feature.main

import com.morteza.storeproject.common.NikeSingleObserver
import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.data.CartItemCount
import com.morteza.storeproject.data.TokenContainer
import com.morteza.storeproject.data.repo.cart.CartRepository
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class MainViewModel(private val cartRepository: CartRepository) : NikeViewModel() {

	fun getCartItemsCount() {
		if (!TokenContainer.token.isNullOrEmpty()) {
			cartRepository.getCartItemCount()
				.subscribeOn(Schedulers.io())
				.subscribe(object : NikeSingleObserver<CartItemCount>(compositeDisposable) {
					override fun onSuccess(itemCount: CartItemCount) {
						Timber.i("getCartItemsCount ===== >>> ${itemCount.count}")
						EventBus.getDefault().postSticky(itemCount)
					}
				})
		}
	}
}