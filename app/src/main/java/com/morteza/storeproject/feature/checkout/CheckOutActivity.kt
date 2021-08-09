package com.morteza.storeproject.feature.checkout

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.morteza.storeproject.R
import com.morteza.storeproject.common.KEY_ID
import com.morteza.storeproject.common.formatPriceToman
import kotlinx.android.synthetic.main.activity_check_out.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : AppCompatActivity() {

	val viewModel: CheckoutViewModel by viewModel {
		val uri: Uri? = intent.data
		if (intent.data != null)
		//browser
			parametersOf(intent.data!!.getQueryParameter("order_id")!!.toInt())
		else
		//activity
			parametersOf(intent.extras!!.getInt(KEY_ID))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_check_out)

		viewModel.checkoutLiveData.observe(this) {
			orderPriceTv.text = formatPriceToman(it.payable_price)
			orderStatusTv.text = it.payment_status
			purchaseStatusTv.text =
				if (it.purchase_success) "خرید با موفقیت انجام شد" else "خرید ناموفق"
		}
	}
}