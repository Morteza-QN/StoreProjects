package com.morteza.storeproject.data.repo.source.banner

import com.morteza.storeproject.data.Banner
import io.reactivex.Single

interface BannerDataSource {
	fun getBanner(): Single<List<Banner>>
}