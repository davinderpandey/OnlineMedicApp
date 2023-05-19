package com.app.onlinemedic.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.onlinemedic.R
import com.app.onlinemedic.commoners.AppUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        searchProduct.setOnClickListener {
                startActivity(Intent(this, SearchProductsActivity::class.java).putExtra("heading","Search Products"))
                AppUtils.animateEnterRight(this@MainActivity)
        }
        layoutConsult?.setOnClickListener {
                startActivity(Intent(this, ConsultDoctorActivity::class.java))
                AppUtils.animateEnterRight(this@MainActivity)
        }
        layoutTopProducts?.setOnClickListener {
            startActivity(Intent(this, SearchProductsActivity::class.java).putExtra("heading","Top Products"))
            AppUtils.animateEnterRight(this@MainActivity)
        }
        layoutMedicine?.setOnClickListener {
            startActivity(Intent(this, SearchProductsActivity::class.java).putExtra("heading","Medicine"))
            AppUtils.animateEnterRight(this@MainActivity)
        }
        layoutHealthCare?.setOnClickListener {
            startActivity(Intent(this, HealthCheckupActivity::class.java).putExtra("heading","HealthCare"))
            AppUtils.animateEnterRight(this@MainActivity)
        }
        layoutHealthCheckup?.setOnClickListener {
            startActivity(Intent(this, HealthCheckupActivity::class.java).putExtra("heading","HealthCheckup"))
            AppUtils.animateEnterRight(this@MainActivity)
        }
        layoutlabTest?.setOnClickListener {
            startActivity(Intent(this, HealthCheckupActivity::class.java).putExtra("heading","Lab Test"))
            AppUtils.animateEnterRight(this@MainActivity)
        }
        layoutCovidCare?.setOnClickListener {
            startActivity(Intent(this, InformationActivity::class.java).putExtra("heading","CovidCare"))
            AppUtils.animateEnterRight(this@MainActivity)
        }
        layoutFever?.setOnClickListener {
            startActivity(Intent(this, InformationActivity::class.java).putExtra("heading","Fever and Infection"))
            AppUtils.animateEnterRight(this@MainActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        bottom_nav.selectedItemId = R.id.home
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    startActivity(Intent(this, EditProfileActivity::class.java))
                    AppUtils.animateEnterRight(this@MainActivity)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.heathcare -> {
                        startActivity(Intent(this, HealthCheckupActivity::class.java).putExtra("heading","HealthCare"))
                        AppUtils.animateEnterRight(this@MainActivity)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "ONLINE MEDIC APP"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }


}