package com.app.onlinemedic.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.app.onlinemedic.R
import com.app.onlinemedic.commoners.AppUtils
import com.app.onlinemedic.commoners.BaseActivity
import com.app.onlinemedic.model.MedicineInfo
import com.app.onlinemedic.utils.PreferenceHelper
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import kotlinx.android.synthetic.main.activity_product_detail.*


class ProductDetailActivity : BaseActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setSupportActionBar(toolbar)
        prefs = PreferenceHelper.defaultPrefs(this)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Product Detail"

        val intent: Intent = intent
        val product: MedicineInfo = intent.getSerializableExtra("product") as MedicineInfo

        setData(product)

        addProduct.setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java).putExtra("heading","Add Order"))
            AppUtils.animateEnterRight(this@ProductDetailActivity)
        }
    }

    private fun setData(product: MedicineInfo) {
        try {
            val lorem: Lorem = LoremIpsum.getInstance()
            tvDetail?.text = product.description//lorem.getParagraphs(1, 3)
            tvProductQuantity?.text = "Quantity: ${product.quantity}"
            tvPrice?.text = "Price: ${product.price}"
            tvProduct?.text = product.tab_name

        } catch (e: Exception) {
            Toast.makeText(
                this@ProductDetailActivity,
                "Something went wrong. Please try again in sometime",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AppUtils.animateEnterLeft(this)
    }

}