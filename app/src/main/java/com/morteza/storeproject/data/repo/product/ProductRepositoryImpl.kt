package com.morteza.storeproject.data.repo.product

import com.morteza.storeproject.data.Product
import com.morteza.storeproject.data.repo.source.product.ProductDataSource
import com.morteza.storeproject.data.repo.source.product.ProductLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    private val remoteDataSource: ProductDataSource,
    val localDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun getProducts(sort: Int): Single<List<Product>> = remoteDataSource.getProducts(sort)

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