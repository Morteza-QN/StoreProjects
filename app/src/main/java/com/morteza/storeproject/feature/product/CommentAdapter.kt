package com.morteza.storeproject.feature.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morteza.storeproject.R
import com.morteza.storeproject.data.model.Comment

class CommentAdapter(private val isShowAll: Boolean = false) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

	var comments = ArrayList<Comment>()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val titleTv = itemView.findViewById<TextView>(R.id.commentTitleTv)
		private val dateTv = itemView.findViewById<TextView>(R.id.commentDateTv)
		private val authorTv = itemView.findViewById<TextView>(R.id.commentAuthor)
		private val contentTv = itemView.findViewById<TextView>(R.id.commentContentTv)
		fun bindComment(comment: Comment) {
			titleTv.text = comment.title
			dateTv.text = comment.date
			authorTv.text = comment.author.email
			contentTv.text = comment.content
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bindComment(comments[position])
	}

	override fun getItemCount(): Int {
		return if (comments.size > 3 && !isShowAll) 3 else comments.size
	}


}