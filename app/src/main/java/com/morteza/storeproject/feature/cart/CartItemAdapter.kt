package com.morteza.storeproject.feature.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.morteza.storeproject.R
import com.morteza.storeproject.common.formatPriceToman
import com.morteza.storeproject.data.model.CartItem
import com.morteza.storeproject.data.responce.PurchaseDetail
import com.morteza.storeproject.services.imageLoading.ImageLoadingService

import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_purchase_details.view.*

const val VIEW_TYPE_CART_ITEM = 0
const val VIEW_TYPE_PURCHASE_DETAILS = 1

class CartItemAdapter(
	val cartItems: MutableList<CartItem>,
	val imageLoadingService: ImageLoadingService,
	val cartItemViewCallbacks: CartItemViewCallbacks
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	var purchaseDetail: PurchaseDetail? = null

	inner class CartItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
																	   LayoutContainer {
		fun bindCartItem(cartItem: CartItem) {
			containerView.apply {
				productTitleTv.text = cartItem.product.title
				cartItemCountTv.text = cartItem.count.toString()
				previousPriceTv.text = formatPriceToman(cartItem.product.price + cartItem.product.discount)
				priceTv.text = formatPriceToman(cartItem.product.price)
				removeFromCartBtn.setOnClickListener {
					cartItemViewCallbacks.onRemoveCartItemButtonClick(cartItem)
				}
				changeCountProgressBar.visibility = if (cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.GONE
				cartItemCountTv.visibility = if (cartItem.changeCountProgressBarIsVisible) View.INVISIBLE else View.VISIBLE
				imageLoadingService.load(productIv, cartItem.product.image)
				increaseBtn.setOnClickListener {
					cartItem.changeCountProgressBarIsVisible = true
					containerView.changeCountProgressBar.visibility = View.VISIBLE
					containerView.cartItemCountTv.visibility = View.INVISIBLE
					cartItemViewCallbacks.onIncreaseCartItemButtonClick(cartItem)
				}
				decreaseBtn.setOnClickListener {
					if (cartItem.count > 1) {
						cartItem.changeCountProgressBarIsVisible = true
						containerView.changeCountProgressBar.visibility = View.VISIBLE
						containerView.cartItemCountTv.visibility = View.INVISIBLE
						cartItemViewCallbacks.onDecreaseCartItemButtonClick(cartItem)
					}
				}
				productIv.setOnClickListener {
					cartItemViewCallbacks.onProductImageClick(cartItem)
				}
			}
		}
	}

	class PurchaseDetailViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
																	   LayoutContainer {
		fun bind(totalPrice: Int, shippingCost: Int, payablePrice: Int) {
			containerView.apply {
				totalPriceTv.text = formatPriceToman(totalPrice)
				shippingCostTv.text = formatPriceToman(shippingCost)
				payablePriceTv.text = formatPriceToman(payablePrice)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return if (viewType == VIEW_TYPE_CART_ITEM)
			CartItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false))
		else
			PurchaseDetailViewHolder(
				LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_details, parent, false)
			)
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		if (holder is CartItemViewHolder)
			holder.bindCartItem(cartItems[position])
		else if (holder is PurchaseDetailViewHolder) {
			purchaseDetail?.let {
				holder.bind(it.totalPrice, it.shipping_cost, it.payable_price)
			}
		}
	}

	override fun getItemCount(): Int = cartItems.size + 1

	override fun getItemViewType(position: Int): Int =
		if (position == cartItems.size) VIEW_TYPE_PURCHASE_DETAILS else VIEW_TYPE_CART_ITEM

	fun removeCartItem(cartItem: CartItem) {
		val index = cartItems.indexOf(cartItem)
		if (index > -1) {
			cartItems.removeAt(index)
			notifyItemRemoved(index)
		}
	}

	fun increaseCount(cartItem: CartItem) {
		val index = cartItems.indexOf(cartItem)
		if (index > -1) {
			cartItems[index].changeCountProgressBarIsVisible = false
			notifyItemChanged(index)
		}
	}

	fun decreaseCount(cartItem: CartItem) {
		val index = cartItems.indexOf(cartItem)
		if (index > -1) {
			cartItems[index].changeCountProgressBarIsVisible = false
			notifyItemChanged(index)
		}
	}

	interface CartItemViewCallbacks {
		fun onRemoveCartItemButtonClick(cartItem: CartItem)
		fun onIncreaseCartItemButtonClick(cartItem: CartItem)
		fun onDecreaseCartItemButtonClick(cartItem: CartItem)
		fun onProductImageClick(cartItem: CartItem)
	}
}