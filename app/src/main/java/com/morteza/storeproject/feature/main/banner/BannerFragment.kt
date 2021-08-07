package com.morteza.storeproject.feature.main.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.morteza.storeproject.R
import com.morteza.storeproject.common.KEY_DATA
import com.morteza.storeproject.data.Banner
import com.morteza.storeproject.services.imageLoading.ImageLoadingService
import com.morteza.storeproject.view.NikeImageView
import org.koin.android.ext.android.inject

class BannerFragment : Fragment() {

	private val imageLoadingService: ImageLoadingService by inject()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val imageView =
			inflater.inflate(R.layout.fragment_banner, container, false) as NikeImageView
		val banner = requireArguments().getParcelable<Banner>(KEY_DATA)
			?: throw IllegalMonitorStateException("Banner can not be null")
		imageLoadingService.load(imageView, banner.image)
		return imageView
	}

	companion object {
		fun newInstance(banner: Banner): BannerFragment {
			return BannerFragment().apply {
				arguments = Bundle().apply {
					putParcelable(KEY_DATA, banner)
				}
			}
		}
	}
}