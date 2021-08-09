package com.morteza.storeproject.data.repo.product

import androidx.room.*
import com.morteza.storeproject.data.model.Product
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductLocalDataSource : ProductDataSource {

	override fun getProducts(sort: Int): Single<List<Product>> {
		TODO("remote")
	}

	@Query("SELECT * FROM products")
	override fun getFavoriteProducts(): Single<List<Product>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	override fun addToFavorites(product: Product): Completable

	@Delete
	override fun deleteFromFavorites(product: Product): Completable
}