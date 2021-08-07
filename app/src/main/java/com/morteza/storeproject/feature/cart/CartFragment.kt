package com.morteza.storeproject.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morteza.storeproject.R
import com.morteza.storeproject.common.KEY_DATA
import com.morteza.storeproject.common.NikeCompletableObserver
import com.morteza.storeproject.common.NikeFragment
import com.morteza.storeproject.data.CartItem
import com.morteza.storeproject.feature.auth.AuthActivity
import com.morteza.storeproject.feature.product.ProductDetailsActivity
import com.morteza.storeproject.feature.shipping.ShippingActivity
import com.morteza.storeproject.services.imageLoading.ImageLoadingService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.view_cart_empty_state.*
import kotlinx.android.synthetic.main.view_cart_empty_state.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CartFragment : NikeFragment(), CartItemAdapter.CartItemViewCallbacks {
	val viewModel: CartViewModel by viewModel()
	var cartItemAdapter: CartItemAdapter? = null
	private val imageLoadingService: ImageLoadingService by inject()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_cart, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.progressBarLiveData.observe(viewLifecycleOwner) { setProgressIndicator(it) }
		viewModel.cartItemsLiveData.observe(viewLifecycleOwner) {
			Timber.i(it.toString())
			cartItemAdapter = CartItemAdapter(it as MutableList<CartItem>, imageLoadingService, this)
			cartItemsRv.apply {
				layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
				adapter = cartItemAdapter
			}
		}
		viewModel.purchaseDetailLiveData.observe(viewLifecycleOwner) {
			Timber.i(it.toString())
			cartItemAdapter?.let { adapter ->
				adapter.purchaseDetail = it
				adapter.notifyItemChanged(adapter.cartItems.size)
			}
		}

		viewModel.emptyStateLiveData.observe(viewLifecycleOwner) {
			if (it.mustShow) {
				val emptyState = showEmptyState(R.layout.view_cart_empty_state)

				emptyState?.let { view ->
					view.emptyStateMessageTv.text = getString(it.messageResId)
					view.emptyStateCtaBtn.visibility =
						if (it.mustShowCallToActionButton) View.VISIBLE else View.GONE
					view.emptyStateCtaBtn.setOnClickListener {
						startActivity(Intent(requireContext(), AuthActivity::class.java))
					}
				}
			} else
				emptyStateRootView?.visibility = View.GONE
		}

		payBtn.setOnClickListener {
			startActivity(Intent(requireContext(), ShippingActivity::class.java).apply {
				putExtra(KEY_DATA, viewModel.purchaseDetailLiveData.value)
			})
		}
	}

	override fun onStart() {
		super.onStart()
		viewModel.refresh()
	}

	override fun onRemoveCartItemButtonClick(cartItem: CartItem) {
		viewModel.removeItemFromCart(cartItem)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(object : NikeCompletableObserver(compositeDisposable) {
				override fun onComplete() {
					cartItemAdapter?.removeCartItem(cartItem)
				}
			})
	}

	override fun onIncreaseCartItemButtonClick(cartItem: CartItem) {
		viewModel.increaseCartItemCount(cartItem)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(object : NikeCompletableObserver(compositeDisposable) {
				override fun onComplete() {
					cartItemAdapter?.increaseCount(cartItem)
				}
			})
	}

	override fun onDecreaseCartItemButtonClick(cartItem: CartItem) {
		viewModel.decreaseCartItemCount(cartItem)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(object : NikeCompletableObserver(compositeDisposable) {
				override fun onComplete() {
					cartItemAdapter?.decreaseCount(cartItem)
				}
			})
	}

	override fun onProductImageClick(cartItem: CartItem) {
		startActivity(Intent(requireContext(), ProductDetailsActivity::class.java).apply {
			putExtra(KEY_DATA, cartItem.product)
		})
	}
}