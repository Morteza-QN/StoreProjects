package com.morteza.storeproject.feature.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morteza.storeproject.R
import com.morteza.storeproject.common.formatPriceToman
import com.morteza.storeproject.common.implementSpringAnimationTrait
import com.morteza.storeproject.data.Product
import com.morteza.storeproject.services.imageLoading.ImageLoadingService
import com.morteza.storeproject.view.NikeImageView

class ProductListAdapter(private val imageLoadingService: ImageLoadingService) :
	RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

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
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))

	override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindProduct(products[position])

	override fun getItemCount(): Int = products.size

	interface OnProductClickListener {
		fun onProductClick(product: Product)
	}
}