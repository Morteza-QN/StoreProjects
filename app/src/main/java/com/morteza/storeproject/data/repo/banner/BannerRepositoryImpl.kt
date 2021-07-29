package com.morteza.storeproject.data.repo.banner

import com.morteza.storeproject.data.Banner
import com.morteza.storeproject.data.repo.source.banner.BannerDataSource
import io.reactivex.Single

class BannerRepositoryImpl(private val bannerRemoteDataSource: BannerDataSource) : BannerRepository {
	override fun getBanner(): Single<List<Banner>> = bannerRemoteDataSource.getBanner()
}