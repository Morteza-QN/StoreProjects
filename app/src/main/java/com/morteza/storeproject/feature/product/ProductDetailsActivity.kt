package com.morteza.storeproject.feature.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.morteza.storeproject.R
import com.morteza.storeproject.common.formatPriceToman
import com.morteza.storeproject.services.imageLoading.ImageLoadingService
import com.morteza.storeproject.view.scroll.ObservableScrollViewCallbacks
import com.morteza.storeproject.view.scroll.ScrollState
import kotlinx.android.synthetic.main.activity_product_details.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailsActivity : AppCompatActivity() {
	private val productDetailsViewModel: ProductDetailsViewModel by viewModel { parametersOf(intent.extras) }
	private val imageLoadingService: ImageLoadingService by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_product_details)

		productDetailsViewModel.productLiveData.observe(this) {
			imageLoadingService.load(productIv, it.image)
			productDetailTitleTv.text = it.title
			previousPriceDetailTv.text = formatPriceToman(it.previous_price)
			priceDetailTv.text = formatPriceToman(it.price)
			toolbarTitleTv.text = it.title
		}
		productIv.post {
			val productIvHeight = productIv.height
			val toolbar = toolbarView
			val image = productIv
			observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
				override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
					toolbar.alpha = scrollY.toFloat() / productIvHeight.toFloat()
					image.translationY = scrollY.toFloat() / 2
				}

				override fun onDownMotionEvent() {}
				override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {}
			})
		}
	}
}