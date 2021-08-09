package com.morteza.storeproject.feature.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morteza.storeproject.R
import com.morteza.storeproject.common.formatPriceToman
import com.morteza.storeproject.common.implementSpringAnimationTrait
import com.morteza.storeproject.data.model.Product
import com.morteza.storeproject.services.imageLoading.ImageLoadingService
import com.morteza.storeproject.view.NikeImageView
import timber.log.Timber

const val VIEW_TYPE_ROUND = 0
const val VIEW_TYPE_SMALL = 1
const val VIEW_TYPE_LARGE = 2

class ProductListAdapter(
	var viewType: Int = VIEW_TYPE_ROUND,
	private val imageLoadingService: ImageLoadingService
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

	lateinit var onProductClickListener: OnProductClickListener

	var products = ArrayList<Product>()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val titleTv: TextView = itemView.findViewById(R.id.productTitleTv)
		private val currentPriceTv: TextView = itemView.findViewById(R.id.currentPriceTv)
		private val previousPriceTv: TextView = itemView.findViewById(R.id.previousPriceTv)
		private val productIv: NikeImageView = itemView.findViewById(R.id.productIv)
		private val favoriteBtn: ImageView = itemView.findViewById(R.id.favoriteBtn)

		fun bindProduct(product: Product) {
			imageLoadingService.load(productIv, product.image)
			titleTv.text = product.title
			currentPriceTv.text = formatPriceToman(product.price)
			previousPriceTv.text = formatPriceToman(product.previous_price)
			previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

			itemView.setOnClickListener {
				it.implementSpringAnimationTrait()
				onProductClickListener.onProductClick(product)
			}
			if (product.isFavorite)
				favoriteBtn.setImageResource(R.drawable.ic_favorite_fill)
			else
				favoriteBtn.setImageResource(R.drawable.ic_favorites)

			favoriteBtn.setOnClickListener {
				onProductClickListener.onFavoriteBtnClick(product)
				product.isFavorite = !product.isFavorite
				notifyItemChanged(adapterPosition)
			}
		}
	}

	override fun getItemViewType(position: Int): Int {
		Timber.i("view type = $viewType")
		return viewType
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		Timber.i("view type = $viewType")
		val layoutResId = when (viewType) {
			VIEW_TYPE_ROUND -> R.layout.item_product
			VIEW_TYPE_SMALL -> R.layout.item_product_small
			VIEW_TYPE_LARGE -> R.layout.item_product_large
			else            -> throw IllegalStateException("viewType is not valid")

		}
		return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindProduct(products[position])

	override fun getItemCount(): Int = products.size

	interface OnProductClickListener {
		fun onProductClick(product: Product)
		fun onFavoriteBtnClick(product: Product) {}
	}
}