package com.morteza.storeproject.data.repo.user

import com.morteza.storeproject.data.TokenContainer
import com.morteza.storeproject.data.repo.source.user.UserDataSource
import com.morteza.storeproject.data.responce.TokenResponse
import io.reactivex.Completable

class UserRepositoryImpl(
	private val remote: UserDataSource,
	private val local: UserDataSource
) : UserRepository {
	override fun login(username: String, password: String): Completable {
		return remote.login(username, password).doOnSuccess {
			onSuccessLogin(it)
		}.ignoreElement()
	}

	override fun signUp(username: String, password: String): Completable {
		return remote.signUp(username, password).flatMap {
			remote.login(username, password)
		}.doOnSuccess {
			onSuccessLogin(it)
		}.ignoreElement()
	}

	override fun loadToken() {
		local.loadToken()
	}

	override fun getUserName(): String {
		TODO("Not yet implemented")
	}

	override fun signOut() {
		TODO("Not yet implemented")
	}

	private fun onSuccessLogin(tokenResponse: TokenResponse) {
		TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
		local.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
	}
}