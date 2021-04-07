package com.morteza.storeproject.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.morteza.storeproject.R
import com.morteza.storeproject.common.NikeFragment
import com.morteza.storeproject.common.toPx
import com.morteza.storeproject.data.Product
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : NikeFragment() {
	private val mainViewModel: MainViewModel by viewModel()
	private val productAdapter: ProductListAdapter by inject()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_main, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//		latestProductsRv.linearLayout(requireContext(), RecyclerView.HORIZONTAL)
		latestProductsRv.adapter = productAdapter

		mainViewModel.productLiveData.observe(viewLifecycleOwner) {
			Timber.i(it.toString())
			productAdapter.products = it as ArrayList<Product>
		}

		mainViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
			setProgressIndicator(it)
		}
		mainViewModel.bannerLiveData.observe(viewLifecycleOwner) {
			Timber.i(it.toString())
			val bannerSliderAdapter = BannerSliderAdapter(this, it)
			bannerSliderViewPager.adapter = bannerSliderAdapter

			val viewpagerHeight = ((bannerSliderViewPager.measuredWidth - 32.toPx) * 173) / 328
			val layoutParams = bannerSliderViewPager.layoutParams
			layoutParams.height = viewpagerHeight
			bannerSliderViewPager.layoutParams = layoutParams

			slider_indicator.setViewPager2(bannerSliderViewPager)
		}
	}
}