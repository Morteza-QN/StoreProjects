package com.morteza.storeproject.feature.profile

import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.data.TokenContainer
import com.morteza.storeproject.data.repo.user.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : NikeViewModel() {

	val username: String
		get() = userRepository.getUserName()

	val isSignedIn: Boolean
		get() = TokenContainer.token != null

	fun signOut() = userRepository.signOut()
}