package com.morteza.storeproject.data.repo.source.banner

import com.morteza.storeproject.data.Banner
import com.morteza.storeproject.services.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(private val apiService: ApiService) : BannerDataSource {
	override fun getBanner(): Single<List<Banner>> = apiService.getBanners()
}