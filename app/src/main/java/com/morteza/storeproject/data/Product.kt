package com.morteza.storeproject.data

data class Product(
    val discount: Int,
    val id: Int,
    val image: String,
    val previous_price: Int,
    val price: Int,
    val status: Int,
    val title: String
)