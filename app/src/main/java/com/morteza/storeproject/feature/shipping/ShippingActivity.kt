package com.morteza.storeproject.feature.shipping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.morteza.storeproject.R
import com.morteza.storeproject.common.KEY_DATA
import com.morteza.storeproject.data.responce.PurchaseDetail
import com.morteza.storeproject.feature.cart.CartItemAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_shipping.*


class ShippingActivity : AppCompatActivity() {
	//	val viewModel: ShippingViewModel by viewModel()
	val compositeDisposable = CompositeDisposable()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_shipping)

		val purchaseDetail = intent.getParcelableExtra<PurchaseDetail>(KEY_DATA)
			?: throw IllegalStateException("purchase detail cannot be null")

		val viewHolder = CartItemAdapter.PurchaseDetailViewHolder(purchaseDetailView)
		viewHolder.bind(
			purchaseDetail.totalPrice,
			purchaseDetail.shipping_cost,
			purchaseDetail.payable_price
		)

		/*val onClickListener = View.OnClickListener {
			viewModel.submitOrder(
				firstNameEt.text.toString(),
				lastNameEt.text.toString(),
				postalCodeEt.text.toString(),
				phoneNumberEt.text.toString(),
				addressEt.text.toString(),
				if (it.id == R.id.onlinePaymentBtn) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD
			).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(object : NikeSingleObserver<SubmitOrderResult>(compositeDisposable) {
					override fun onSuccess(t: SubmitOrderResult) {
						if (t.bank_gateway_url.isNotEmpty()) {
							openUrlInCustomTab(this@ShippingActivity, t.bank_gateway_url)
						} else {
							startActivity(
								Intent(
									this@ShippingActivity,
									CheckOutActivity::class.java
								).apply {
									putExtra(EXTRA_KEY_ID, t.order_id)
								}
							)
						}
						finish()

					}
				})
		}*/

		/*onlinePaymentBtn.setOnClickListener(onClickListener)
		codBtn.setOnClickListener(onClickListener)*/
	}
}