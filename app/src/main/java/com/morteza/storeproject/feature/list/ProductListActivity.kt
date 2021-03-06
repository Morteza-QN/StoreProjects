package com.morteza.storeproject.feature.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.morteza.storeproject.R
import com.morteza.storeproject.common.KEY_DATA
import com.morteza.storeproject.common.NikeActivity
import com.morteza.storeproject.data.model.Product
import com.morteza.storeproject.feature.main.ProductListAdapter
import com.morteza.storeproject.feature.main.VIEW_TYPE_LARGE
import com.morteza.storeproject.feature.main.VIEW_TYPE_SMALL
import com.morteza.storeproject.feature.product.ProductDetailsActivity

import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductListActivity : NikeActivity(), ProductListAdapter.OnProductClickListener {

	private val viewModel: ProductListViewModel by viewModel { parametersOf(intent.extras!!.getInt(KEY_DATA)) }
	private val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_product_list)

		toolbarView.onClickBackBtnListener = View.OnClickListener { finish() }

		val gridLayoutManager = GridLayoutManager(this, 2)
		productsRv.layoutManager = gridLayoutManager
		productsRv.adapter = productListAdapter
		productListAdapter.onProductClickListener = this

		mObserve()
		viewTypeChangerBtn.setOnClickListener {
			productListAdapter.apply {
				Timber.i("before changed spanCount ${gridLayoutManager.spanCount}")
				if (viewType == VIEW_TYPE_SMALL) {
					viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
					viewType = VIEW_TYPE_LARGE
					gridLayoutManager.spanCount = 1
					notifyDataSetChanged()
					Timber.i("after changed spanCount large ${gridLayoutManager.spanCount}")
				} else {
					viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
					viewType = VIEW_TYPE_SMALL
					gridLayoutManager.spanCount = 2
					notifyDataSetChanged()
					Timber.i("after changed spanCount small ${gridLayoutManager.spanCount}")
				}
			}
		}
		sortBtn.setOnClickListener {
			MaterialAlertDialogBuilder(this)
				.setTitle(getString(R.string.sort))
				.setSingleChoiceItems(R.array.sortTitlesArray, viewModel.getSelectedSort())
				{ dialog, selectedSortIndex ->
					viewModel.updateList(selectedSortIndex)
					dialog.dismiss()
				}.show()
		}

	}

	private fun mObserve() {
		viewModel.productLiveData.observe(this) {
			Timber.i(it.toString())
			productListAdapter.products = it as ArrayList<Product>
		}
		viewModel.selectedSortTitleLiveData.observe(this) {
			selectedSortTitleTv.text = getString(it)
		}
		viewModel.progressBarLiveData.observe(this) {
			setProgressIndicator(it)
		}
	}

	override fun onProductClick(product: Product) {
		startActivity(Intent(this, ProductDetailsActivity::class.java).apply { putExtra(KEY_DATA, product) })
	}

	override fun onFavoriteBtnClick(product: Product) {

	}
}