package com.morteza.storeproject.data.repo.comment

import com.morteza.storeproject.data.model.Comment
import com.morteza.storeproject.services.http.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService) : CommentDataSource {
	override fun getComments(productId: Int): Single<List<Comment>> {
		return apiService.getComments(productId)
	}

	override fun addComment(): Single<Comment> {
		TODO("Not yet implemented")
	}
}