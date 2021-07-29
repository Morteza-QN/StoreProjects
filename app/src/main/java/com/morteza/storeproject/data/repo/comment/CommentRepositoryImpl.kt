package com.morteza.storeproject.data.repo.comment

import com.morteza.storeproject.data.Comment
import com.morteza.storeproject.data.repo.source.comment.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImpl(private val commentDataSource: CommentDataSource) : CommentRepository {
	override fun getComments(productId: Int): Single<List<Comment>> {
		return commentDataSource.getComments(productId)
	}

	override fun addComment(): Single<Comment> {
		TODO("Not yet implemented")
	}
}