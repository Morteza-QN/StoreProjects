package com.morteza.storeproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.morteza.storeproject.data.model.Product
import com.morteza.storeproject.data.repo.product.ProductLocalDataSource


@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

	abstract fun getProductDao(): ProductLocalDataSource
}