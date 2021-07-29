package com.morteza.storeproject.data.repo.comment

import com.morteza.storeproject.data.Comment
import io.reactivex.Single

interface CommentRepository {

	fun getComments(productId: Int): Single<List<Comment>>

	fun addComment(): Single<Comment>
}