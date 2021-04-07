package com.morteza.storeproject.data.repo

import com.morteza.storeproject.data.Banner
import io.reactivex.Single

interface BannerRepository {
	fun getBanner(): Single<List<Banner>>
}