package com.app.onlinemedic.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.app.onlinemedic.R
import com.app.onlinemedic.commoners.AppUtils
import com.app.onlinemedic.commoners.BaseActivity
import com.app.onlinemedic.model.MedicineInfo
import com.app.onlinemedic.model.OrderInfo
import com.app.onlinemedic.utils.PreferenceHelper
import kotlinx.android.synthetic.main.activity_order.*
import java.util.*

class OrderActivity : BaseActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        setSupportActionBar(toolbar)
        prefs = PreferenceHelper.defaultPrefs(this)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val heading = intent.getStringExtra("heading")
        if (heading != null)
            supportActionBar?.title = heading
        else
            supportActionBar?.title = "Health Checkup"

        if((heading?:"").toLowerCase(Locale.US) != "healthcare"){
            profileAvatar.visibility = View.GONE
        }

        consultButton.setOnClickListener { callDoctor() }
    }

    private fun callDoctor() {
//        val callIntent = Intent(Intent.ACTION_DIAL)
//        callIntent.data = Uri.parse("tel:"+tvMobile.text.toString())
//        startActivity(callIntent)

        val orderDescription = orderDesc.text.toString()
        val orderTime = tvOrderTime.text.toString()
        val orderDate = tvOrderTime.text.toString()
        val orderName = tvOrdername.text.toString()
        val orderObj = OrderInfo(1,orderName,orderDate,orderTime,orderDescription)
        orderObj.save()
        OrderInfo.listAll(OrderInfo::class.java)

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