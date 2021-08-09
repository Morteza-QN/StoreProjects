package com.morteza.storeproject.feature.cart

import androidx.lifecycle.MutableLiveData
import com.morteza.storeproject.R
import com.morteza.storeproject.common.NikeSingleObserver
import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.common.asyncNetworkRequest
import com.morteza.storeproject.data.TokenContainer
import com.morteza.storeproject.data.model.CartItem
import com.morteza.storeproject.data.model.CartItemCount
import com.morteza.storeproject.data.model.EmptyState
import com.morteza.storeproject.data.repo.cart.CartRepository
import com.morteza.storeproject.data.responce.CartResponse
import com.morteza.storeproject.data.responce.PurchaseDetail
import io.reactivex.Completable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class CartViewModel(private val cartRepository: CartRepository) : NikeViewModel() {
	val cartItemsLiveData = MutableLiveData<List<CartItem>>()
	val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
	val emptyStateLiveData = MutableLiveData<EmptyState>()

	fun removeItemFromCart(cartItem: CartItem): Completable {
		return cartRepository.remove(cartItem.cart_item_id)
			.doAfterSuccess {
				Timber.i("Cart Items Count after remove -> ${cartItemsLiveData.value?.size}")
				calculateAndPublishPurchaseDetail()
				val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
				Timber.i("removeItemFromCart --- >>> ${cartItemCount.count}")
				cartItemCount?.let {
					it.count -= cartItem.count
					EventBus.getDefault().postSticky(it)
				}
				cartItemsLiveData.value?.let { listCartItems ->
					if (listCartItems.isEmpty()) {
						emptyStateLiveData.postValue(EmptyState(true, R.string.cartEmptyState))
					}
				}
			}.ignoreElement()
	}

	fun increaseCartItemCount(cartItem: CartItem): Completable {
		return cartRepository.changeCount(cartItem.cart_item_id, ++cartItem.count)
			.doAfterSuccess {
				calculateAndPublishPurchaseDetail()
				val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
				Timber.i("increaseCartItemCount --- >>> ${cartItemCount.count}")
				cartItemCount?.let { item ->
					item.count += 1
					EventBus.getDefault().postSticky(item)
				}
			}.ignoreElement()
	}

	fun decreaseCartItemCount(cartItem: CartItem): Completable {
		return cartRepository.changeCount(cartItem.cart_item_id, --cartItem.count)
			.doAfterSuccess {
				calculateAndPublishPurchaseDetail()
				val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
				Timber.i("decreaseCartItemCount --- >>> ${cartItemCount.count}")
				cartItemCount?.let {
					it.count -= 1
					EventBus.getDefault().postSticky(it)
				}
			}.ignoreElement()
	}

	fun refresh() {
		getCartItems()
	}

	private fun getCartItems() {
		if (!TokenContainer.token.isNullOrEmpty()) {
			progressBarLiveData.value = true
			emptyStateLiveData.value = EmptyState(false)

			cartRepository.get()
				.asyncNetworkRequest()
				.doFinally { progressBarLiveData.value = false }
				.subscribe(object : NikeSingleObserver<CartResponse>(compositeDisposable) {
					override fun onSuccess(data: CartResponse) {
						if (data.cart_items.isNotEmpty()) {
							cartItemsLiveData.value = data.cart_items
							purchaseDetailLiveData.value =
								PurchaseDetail(data.total_price, data.shipping_cost, data.payable_price)
						} else
							emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyState)
					}
				})
		} else
			emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyStateLoginRequired, true)
	}

	private fun calculateAndPublishPurchaseDetail() {
		cartItemsLiveData.value?.let { cartItems ->
			purchaseDetailLiveData.value?.let { purchaseDetail ->
				var totalPrice = 0
				var payablePrice = 0
				cartItems.forEach {
					totalPrice += it.product.price * it.count
					payablePrice += (it.product.price - it.product.discount) * it.count
				}
				purchaseDetail.totalPrice = totalPrice
				purchaseDetail.payable_price = payablePrice
				purchaseDetailLiveData.postValue(purchaseDetail)
			}
		}
	}
}