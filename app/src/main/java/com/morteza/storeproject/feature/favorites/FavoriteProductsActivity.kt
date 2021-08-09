package com.morteza.storeproject.feature.favorites

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.morteza.storeproject.R
import com.morteza.storeproject.common.KEY_DATA
import com.morteza.storeproject.common.NikeActivity
import com.morteza.storeproject.data.model.Product
import com.morteza.storeproject.feature.product.ProductDetailsActivity
import kotlinx.android.synthetic.main.activity_favorite_products.*
import kotlinx.android.synthetic.main.view_default_empty_state.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteProductsActivity : NikeActivity(), FavoriteProductsAdapter.FavoriteProductEventListener {
	val viewModel: FavoriteProductsViewModel by viewModel()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_favorite_products)

		helpBtn.setOnClickListener {
//			Snackbar.make(it, R.string.favorites_help_message, Snackbar.LENGTH_LONG).show()
			showSnackBar(getString(R.string.favorites_help_message), Snackbar.LENGTH_LONG)
		}

		viewModel.productsLiveData.observe(this) {
			if (it.isNotEmpty()) {
				favoriteProductsRv.layoutManager =
					LinearLayoutManager(this, RecyclerView.VERTICAL, false)
				favoriteProductsRv.adapter =
					FavoriteProductsAdapter(it as MutableList<Product>, this, get())
			} else {
				showEmptyState(R.layout.view_default_empty_state)
				emptyStateMessageTv.text = getString(R.string.favorites_empty_state_message)
			}
		}

	}

	override fun onClick(product: Product) {
		startActivity(Intent(this, ProductDetailsActivity::class.java).apply {
			putExtra(KEY_DATA, product)
		})
	}

	override fun onLongClick(product: Product) {
		viewModel.removeFromFavorites(product)
	}
}