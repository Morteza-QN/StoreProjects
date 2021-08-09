package com.morteza.storeproject.services.http

import com.google.gson.JsonObject
import com.morteza.storeproject.data.TokenContainer
import com.morteza.storeproject.data.repo.user.CLIENT_ID
import com.morteza.storeproject.data.repo.user.CLIENT_SECRET
import com.morteza.storeproject.data.repo.user.UserDataSource
import com.morteza.storeproject.data.responce.TokenResponse
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class NikeAuthenticator : Authenticator, KoinComponent {
	private val apiService: ApiService by inject()
	private val userLocalDataSource: UserDataSource by inject()
	override fun authenticate(route: Route?, response: Response): Request? {
		Timber.i("---->> token ${TokenContainer.token != null} --tokenRef ${TokenContainer.refreshToken != null} --")
		Timber.i("---->> lastRes ${!response.request.url.pathSegments.last().equals("token", false)} ")
		if (TokenContainer.token != null &&
			TokenContainer.refreshToken != null &&
			!response.request.url.pathSegments.last().equals("token", false)
		) {
			try {
				val token = refreshToken()
				if (token.isEmpty())
					return null

				return response.request.newBuilder().header("Authorization", "Bearer $token")
					.build()

			} catch (exception: Exception) {
				Timber.e(exception)
			}
		}

		return null
	}

	fun refreshToken(): String {
		val response: retrofit2.Response<TokenResponse> =
			apiService.refreshToken(JsonObject().apply {
				addProperty("grant_type", "refresh_token")
				addProperty("refresh_token", TokenContainer.refreshToken)
				addProperty("client_id", CLIENT_ID)
				addProperty("client_secret", CLIENT_SECRET)
			}).execute()
		response.body()?.let {
			TokenContainer.update(it.access_token, it.refresh_token)
			userLocalDataSource.saveToken(it.access_token, it.refresh_token)
			return it.access_token
		}
		return ""
	}
}