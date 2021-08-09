package com.morteza.storeproject.data.repo.product

import com.morteza.storeproject.data.model.Product
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
	private val remote: ProductDataSource,
	private val local: ProductLocalDataSource
) : ProductRepository {
	override fun getProducts(sort: Int): Single<List<Product>> =
		local.getFavoriteProducts().flatMap { favoriteProducts ->
			remote.getProducts(sort)
				.doOnSuccess { products ->
					val favoriteProductIds = favoriteProducts.map { it.id }
					products.forEach { product ->
						if (favoriteProductIds.contains(product.id))
							product.isFavorite = true
					}
				}
		}

	override fun getFavoriteProducts(): Single<List<Product>> = local.getFavoriteProducts()

	override fun addToFavorites(product: Product): Completable = local.addToFavorites(product)

	override fun deleteFromFavorites(product: Product): Completable = local.deleteFromFavorites(product)
}