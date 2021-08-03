package com.morteza.storeproject.feature.auth

import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.data.repo.user.UserRepository
import io.reactivex.Completable

class AuthViewModel(private val userRepository: UserRepository) : NikeViewModel() {

	fun login(email: String, password: String): Completable {
		showProgressbar(true)
		return userRepository.login(email, password).doFinally {
			progressBarLiveData.postValue(false)
		}
	}

	fun signUp(email: String, password: String): Completable {
		progressBarLiveData.value = true
		return userRepository.signUp(email, password).doFinally {
			progressBarLiveData.postValue(false)
		}
	}
}