package com.morteza.storeproject.data.repo.comment

import com.morteza.storeproject.data.model.Comment
import io.reactivex.Single

interface CommentDataSource {

	fun getComments(productId: Int): Single<List<Comment>>

	fun addComment(): Single<Comment>
}