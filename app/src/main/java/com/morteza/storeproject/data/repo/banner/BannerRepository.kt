package com.morteza.storeproject.data.repo.banner

import com.morteza.storeproject.data.model.Banner
import io.reactivex.Single

interface BannerRepository {
	fun getBanner(): Single<List<Banner>>
}