package com.morteza.storeproject.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.morteza.storeproject.R
import com.morteza.storeproject.common.KEY_DATA
import com.morteza.storeproject.common.NikeFragment
import com.morteza.storeproject.common.linearLayout
import com.morteza.storeproject.common.toPx
import com.morteza.storeproject.data.Product
import com.morteza.storeproject.data.SORT_LATEST
import com.morteza.storeproject.feature.list.ProductListActivity
import com.morteza.storeproject.feature.main.ProductListAdapter
import com.morteza.storeproject.feature.main.VIEW_TYPE_ROUND
import com.morteza.storeproject.feature.main.banner.BannerSliderAdapter
import com.morteza.storeproject.feature.product.ProductDetailsActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class HomeFragment : NikeFragment(), ProductListAdapter.OnProductClickListener {

	private val homeViewModel: HomeViewModel by viewModel()
	private val productAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_ROUND) }
	var viewpagerHeight: Int = 0
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_home, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		productAdapter.onProductClickListener = this
		latestProductsRv.apply {
			linearLayout(requireContext(), RecyclerView.HORIZONTAL)
			adapter = productAdapter
		}
		homeViewModel.productLiveData.observe(viewLifecycleOwner) {
			Timber.i(it.toString())
			productAdapter.products = it as ArrayList<Product>
		}
		viewLatestProducts.setOnClickListener {
			startActivity(
				Intent(requireContext(), ProductListActivity::class.java).apply { putExtra(KEY_DATA, SORT_LATEST) })
		}
		homeViewModel.progressBarLiveData.observe(viewLifecycleOwner) { setProgressIndicator(it) }
		homeViewModel.bannerLiveData.observe(viewLifecycleOwner) {
			Timber.i(it.toString())


			val bannerSliderAdapter = BannerSliderAdapter(this, it)
			bannerSliderViewPager.adapter = bannerSliderAdapter

			viewpagerHeight = ((bannerSliderViewPager.width - 32.toPx) * 173) / 328
			val layoutParams = bannerSliderViewPager.layoutParams
			layoutParams.height = viewpagerHeight
			bannerSliderViewPager.layoutParams = layoutParams
			Timber.i("-------->${bannerSliderViewPager.width}  -- ${bannerSliderViewPager.measuredWidth}-- ${bannerSliderViewPager.measuredWidthAndState}")
			slider_indicator.setViewPager2(bannerSliderViewPager)
		}
	}

	override fun onProductClick(product: Product) {
		startActivity(Intent(requireContext(), ProductDetailsActivity::class.java).apply {
			putExtra(KEY_DATA, product)
		})
	}
}