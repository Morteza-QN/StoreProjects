package com.morteza.storeproject.data.repo.user

import com.morteza.storeproject.data.TokenContainer
import com.morteza.storeproject.data.responce.TokenResponse
import io.reactivex.Completable

class UserRepositoryImpl(private val remote: UserDataSource, private val local: UserDataSource) : UserRepository {

	override fun login(username: String, password: String): Completable {
		return remote.login(username, password)
			.doOnSuccess { tokenResponse ->
				onSuccessLogin(username, tokenResponse)
			}.ignoreElement()
	}

	override fun signUp(username: String, password: String): Completable {
		return remote.signUp(username, password).flatMap {
			remote.login(username, password)
		}.doOnSuccess { tokenResponse ->
			onSuccessLogin(username, tokenResponse)
		}.ignoreElement()
	}

	override fun loadToken() = local.loadToken()

	override fun getUserName(): String = local.getUsername()

	override fun signOut() {
		local.signOut()
		TokenContainer.update(null, null)
	}

	private fun onSuccessLogin(username: String, tokenResponse: TokenResponse) {
		TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
		local.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
		local.saveUsername(username)
	}
}