package com.morteza.storeproject.data.repo

import com.morteza.storeproject.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProducts(): Single<List<Product>>
    fun getFavoriteProducts(): Single<List<Product>>
    fun addToFavorites(): Completable
    fun deleteToFavorites(): Completable
}