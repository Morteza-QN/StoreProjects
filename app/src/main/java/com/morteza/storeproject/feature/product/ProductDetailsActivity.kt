package com.morteza.storeproject.feature.product

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morteza.storeproject.R
import com.morteza.storeproject.common.KEY_ID
import com.morteza.storeproject.common.NikeActivity
import com.morteza.storeproject.common.NikeCompletableObserver
import com.morteza.storeproject.common.formatPriceToman
import com.morteza.storeproject.data.model.Comment
import com.morteza.storeproject.feature.product.comment.CommentListActivity
import com.morteza.storeproject.services.imageLoading.ImageLoadingService
import com.morteza.storeproject.view.scroll.ObservableScrollViewCallbacks
import com.morteza.storeproject.view.scroll.ScrollState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_details.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetailsActivity : NikeActivity() {
	private val viewModel: ProductDetailsViewModel by viewModel { parametersOf(intent.extras) }
	private val imageLoadingService: ImageLoadingService by inject()
	private val commentAdapter = CommentAdapter(false)
	private val compositeDisposable = CompositeDisposable()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_product_details)
		mObserve()

		commentsRv.apply {
			layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
			adapter = commentAdapter
			isNestedScrollingEnabled = false
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

		addToCartBtn.setOnClickListener {
			viewModel.postAddToCart()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(object : NikeCompletableObserver(compositeDisposable) {
					override fun onComplete() {
						showSnackBar(getString(R.string.success_addToCart))
					}
				})
		}
	}

	private fun mObserve() {
		viewModel.progressBarLiveData.observe(this) {
			setProgressIndicator(it)
		}
		viewModel.productLiveData.observe(this) {
			imageLoadingService.load(productIv, it.image)
			productDetailTitleTv.text = it.title
			previousPriceDetailTv.apply {
				text = formatPriceToman(it.previous_price)
				paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
			}
			priceDetailTv.text = formatPriceToman(it.price)
			toolbarTitleTv.text = it.title
		}
		viewModel.commentsLiveData.observe(this) {
			Timber.i(it.toString())
			commentAdapter.comments = it as ArrayList<Comment>
			if (it.size > 3) viewAllCommentsBtn.visibility = View.VISIBLE
			viewAllCommentsBtn.setOnClickListener {
				startActivity(Intent(this, CommentListActivity::class.java).apply {
					putExtra(KEY_ID, viewModel.productLiveData.value!!.id)
				})
			}
		}
	}

	override fun onDestroy() {
		compositeDisposable.clear()
		super.onDestroy()
	}
}