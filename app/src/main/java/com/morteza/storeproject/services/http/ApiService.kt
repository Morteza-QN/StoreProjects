package com.morteza.storeproject.services.http

import com.google.gson.JsonObject
import com.morteza.storeproject.data.*
import com.morteza.storeproject.data.responce.AddToCartResponse
import com.morteza.storeproject.data.responce.CartResponse
import com.morteza.storeproject.data.responce.MessageResponse
import com.morteza.storeproject.data.responce.TokenResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

	@GET("product/list")
	fun getProducts(@Query("sort") sort: String): Single<List<Product>>

	@GET("banner/slider")
	fun getBanners(): Single<List<Banner>>

	@GET("comment/list")
	fun getComments(@Query("product_id") productId: Int): Single<List<Comment>>

	@POST("cart/add")
	fun addToCart(@Body jsonObject: JsonObject): Single<AddToCartResponse>

	@POST("cart/remove")
	fun removeItemFromCart(@Body jsonObject: JsonObject): Single<MessageResponse>

	@GET("cart/list")
	fun getCart(): Single<CartResponse>

	@POST("cart/changeCount")
	fun changeCount(@Body jsonObject: JsonObject): Single<AddToCartResponse>

	@GET("cart/count")
	fun getCartItemsCount(): Single<CartItemCount>

	@POST("auth/token")
	fun login(@Body jsonObject: JsonObject): Single<TokenResponse>

	@POST("user/register")
	fun signUp(@Body jsonObject: JsonObject): Single<MessageResponse>

	@POST("auth/token")
	fun refreshToken(@Body jsonObject: JsonObject): Call<TokenResponse>
}

private fun getMyOkHttpClient(): OkHttpClient.Builder {

	return OkHttpClient.Builder()
		.addInterceptor(HttpLoggingInterceptor().apply {
			setLevel(HttpLoggingInterceptor.Level.BODY)
		})
		.addInterceptor {
			val oldRequest = it.request()
			val newRequestBuilder = oldRequest.newBuilder()
			if (TokenContainer.token != null)
				newRequestBuilder.addHeader("Authorization", "Bearer ${TokenContainer.token}")
			newRequestBuilder.addHeader("Accept", "application/json")
			newRequestBuilder.method(oldRequest.method, oldRequest.body)
			return@addInterceptor it.proceed(newRequestBuilder.build())

		}
}

fun createApiServiceInstance(): ApiService {
	val okHttpClient = OkHttpClient.Builder()
		.addInterceptor {
			val oldRequest = it.request()
			val newRequestBuilder = oldRequest.newBuilder()
			if (TokenContainer.token != null)
				newRequestBuilder.addHeader("Authorization", "Bearer ${TokenContainer.token}")

			newRequestBuilder.addHeader("Accept", "application/json")
			newRequestBuilder.method(oldRequest.method, oldRequest.body)
			return@addInterceptor it.proceed(newRequestBuilder.build())
		}
		.addInterceptor(HttpLoggingInterceptor().apply {
			setLevel(HttpLoggingInterceptor.Level.BODY) //log request in cat log
		})
		.build()

	val retrofit = Retrofit.Builder()
		.baseUrl("http://expertdevelopers.ir/api/v1/")
		.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
		.addConverterFactory(GsonConverterFactory.create())
		.build()
	return retrofit.create(ApiService::class.java)
}