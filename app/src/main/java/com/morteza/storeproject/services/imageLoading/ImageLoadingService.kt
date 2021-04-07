package com.morteza.storeproject.services.imageLoading

import com.morteza.storeproject.view.NikeImageView

interface ImageLoadingService {
	fun load(imageView: NikeImageView, imageUrl: String)
}