package com.morteza.storeproject.data.repo.source

import com.morteza.storeproject.data.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductLocalDataSource : ProductDataSource {
    override fun getProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

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