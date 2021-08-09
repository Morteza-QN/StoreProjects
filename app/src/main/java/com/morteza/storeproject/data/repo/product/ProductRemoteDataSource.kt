package com.morteza.storeproject.data.repo.product

import com.morteza.storeproject.data.model.Product
import com.morteza.storeproject.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(private val apiService: ApiService) : ProductDataSource {

	override fun getProducts(sort: Int): Single<List<Product>> = apiService.getProducts(sort.toString())

	override fun getFavoriteProducts(): Single<List<Product>> {
		TODO("local")
	}

	override fun addToFavorites(product: Product): Completable {
		TODO("local")
	}

	override fun deleteFromFavorites(product: Product): Completable {
		TODO("local")
	}
}