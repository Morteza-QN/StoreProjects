package com.morteza.storeproject.data.repo.source.comment

import com.morteza.storeproject.data.Comment
import io.reactivex.Single

interface CommentDataSource {

	fun getComments(productId: Int): Single<List<Comment>>

	fun addComment(): Single<Comment>
}