package com.morteza.storeproject.data.repo.banner

import com.morteza.storeproject.data.model.Banner
import io.reactivex.Single

interface BannerDataSource {
	fun getBanner(): Single<List<Banner>>
}