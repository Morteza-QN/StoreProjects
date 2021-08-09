package com.morteza.storeproject.data.repo.user

import com.google.gson.JsonObject
import com.morteza.storeproject.data.responce.MessageResponse
import com.morteza.storeproject.data.responce.TokenResponse
import com.morteza.storeproject.services.http.ApiService
import io.reactivex.Single

const val CLIENT_ID = 2
const val CLIENT_SECRET = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"

class UserRemoteDataSource(private val apiService: ApiService) : UserDataSource {

	override fun login(username: String, password: String): Single<TokenResponse> {
		return apiService.login(JsonObject().apply {
			addProperty("username", username)
			addProperty("password", password)
			addProperty("grant_type", "password")
			addProperty("client_id", CLIENT_ID)
			addProperty("client_secret", CLIENT_SECRET)
		})
	}

	override fun signUp(username: String, password: String): Single<MessageResponse> {
		return apiService.signUp(JsonObject().apply {
			addProperty("email", username)
			addProperty("password", password)
		})
	}

	override fun loadToken() {
		TODO("local")
	}

	override fun saveToken(token: String, refreshToken: String) {
		TODO("local")
	}

	override fun saveUsername(username: String) {
		TODO("local")
	}

	override fun getUsername(): String {
		TODO("local")
	}

	override fun signOut() {
		TODO("local")
	}
}