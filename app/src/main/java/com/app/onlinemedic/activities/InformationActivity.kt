package com.app.onlinemedic.activities

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import com.app.onlinemedic.R
import com.app.onlinemedic.commoners.AppUtils
import com.app.onlinemedic.commoners.BaseActivity
import com.app.onlinemedic.utils.PreferenceHelper
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import kotlinx.android.synthetic.main.activity_information.*

class InformationActivity : BaseActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        setSupportActionBar(toolbar)
        prefs = PreferenceHelper.defaultPrefs(this)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val heading = intent.getStringExtra("heading")
        if (heading != null)
            supportActionBar?.title = heading
        else
            supportActionBar?.title = "Covid"
        consultButton.setOnClickListener { callDoctor() }
        val lorem: Lorem = LoremIpsum.getInstance()
       textViewInfo.text = lorem.getParagraphs(2, 3)
    }

    private fun callDoctor() {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:999")
        startActivity(callIntent)
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