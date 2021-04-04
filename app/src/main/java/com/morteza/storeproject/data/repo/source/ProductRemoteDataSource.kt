package com.morteza.storeproject.data.repo.source

import com.morteza.storeproject.data.Product
import com.morteza.storeproject.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService) : ProductDataSource {

    override fun getProducts(): Single<List<Product>> = apiService.getProducts()

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteToFavorites(): Completable {
        TODO("Not yet implemented")
    }
}