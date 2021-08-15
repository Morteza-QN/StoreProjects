package com.morteza.storeproject.feature.product.comment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morteza.storeproject.R
import com.morteza.storeproject.common.KEY_ID
import com.morteza.storeproject.common.NikeActivity
import com.morteza.storeproject.data.model.Comment
import com.morteza.storeproject.feature.product.CommentAdapter
import kotlinx.android.synthetic.main.activity_comment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {

	val viewModel: CommentListViewModel by viewModel { parametersOf(intent.extras!!.getInt(KEY_ID)) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_comment_list)
		viewModel.progressBarLiveData.observe(this) { setProgressIndicator(it) }
		viewModel.commentsLiveData.observe(this) {
			val adapter = CommentAdapter(true)
			commentsAllRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
			adapter.comments = it as ArrayList<Comment>
			commentsAllRv.adapter = adapter

		}
		commentListToolbar.onClickBackBtnListener = View.OnClickListener { finish() }
	}

}