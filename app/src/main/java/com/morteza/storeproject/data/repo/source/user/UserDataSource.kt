package com.morteza.storeproject.data.repo.source.user

import com.morteza.storeproject.data.responce.MessageResponse
import com.morteza.storeproject.data.responce.TokenResponse
import io.reactivex.Single

interface UserDataSource {

	fun login(username: String, password: String): Single<TokenResponse>
	fun signUp(username: String, password: String): Single<MessageResponse>
	fun loadToken()
	fun saveToken(token: String, refreshToken: String)
	fun saveUsername(username: String)
	fun getUsername(): String
	fun signOut()
}