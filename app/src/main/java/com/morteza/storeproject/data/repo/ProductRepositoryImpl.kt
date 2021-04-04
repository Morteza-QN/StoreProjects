package com.morteza.storeproject.data.repo

import com.morteza.storeproject.data.Product
import com.morteza.storeproject.data.repo.source.ProductDataSource
import com.morteza.storeproject.data.repo.source.ProductLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    private val remoteDataSource: ProductDataSource,
    val localDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun getProducts(): Single<List<Product>> = remoteDataSource.getProducts()

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