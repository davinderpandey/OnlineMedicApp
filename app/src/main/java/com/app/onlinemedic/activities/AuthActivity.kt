package com.app.onlinemedic.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.app.onlinemedic.R
import com.app.onlinemedic.commoners.BaseActivity
import com.app.onlinemedic.commoners.PrefData
import com.app.onlinemedic.fragments.LoginFragment
import com.app.onlinemedic.model.MedicineInfo
import com.app.onlinemedic.utils.PreferenceHelper
import com.app.onlinemedic.utils.addFragment

class AuthActivity : BaseActivity() {
    private lateinit var loginFragment: LoginFragment
    private var doubleBackToExit = false
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkIfLoggedIn()) {
            setContentView(R.layout.activity_auth)
            addProductstoDB()
            loginFragment = LoginFragment()

            addFragment(loginFragment, R.id.authHolder)
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }
    }

    private fun checkIfLoggedIn(): Boolean {
        prefs = PreferenceHelper.defaultPrefs(this@AuthActivity)
        return (prefs.getString(PrefData.EMAIL, "") ?: "").isNotEmpty()
    }

    override fun onBackPressed() {
        if (!loginFragment.backPressOkay()) {
            Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show()
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            if (doubleBackToExit) {
                super.onBackPressed()
            } else {
                Toast.makeText(this, "Tap back again to exit", Toast.LENGTH_SHORT).show()
                doubleBackToExit = true
                Handler().postDelayed({ doubleBackToExit = false }, 1500)
            }
        }
    }

    private fun addProductstoDB() {
        try{
            MedicineInfo.deleteAll(MedicineInfo::class.java)
            val medList = MedicineInfo.listAll(MedicineInfo::class.java)
            if(medList.isNullOrEmpty()){
                saveProducts()
            }
        }
        catch (e:Exception){
            saveProducts()
        }

    }

    private fun saveProducts() {
        val medInfo1 = MedicineInfo(
            1,
            "Paracetamol",
            "15 in a stripe",
            "$14","ghvb"
        )
        val medInfo2 = MedicineInfo(
            2,
            "Alrid",
            "15 in a stripe",
            "$24","ghvb"
        )
        val medInfo3 = MedicineInfo(
            3,
            "Lyser D",
            "10 in a stripe",
            "$34","ghvb"
        )
        val medInfo4 = MedicineInfo(
            4,
            "Cough syrup",
            "20 in a stripe",
            "$14","ghvb"
        )
        val medInfo5 = MedicineInfo(
            5,
            "Betadine",
            "10gm in a tube",
            "$24","ghvb"
        )
        val medInfo6 = MedicineInfo(
            6,
            "Gelusil",
            "10ml bottle",
            "$34","ghvb"
        )

        val medInfo7 = MedicineInfo(
            7,
            "Sizodon",
            "15 in a stripe",
            "$24","ghvb"
        )
        val medInfo8 = MedicineInfo(
            8,
            "Saridon",
            "10 in a stripe",
            "$34","ghvb"
        )
        val medInfo9 = MedicineInfo(
            9,
            "Odonil",
            "10gm in a tube",
            "$214","ghvb"
        )
        val medInfo10 = MedicineInfo(
            10,
            "Asthaline",
            "4 in a stripe",
            "$124","ghvb"
        )
        try {
            medInfo1.save()
            medInfo2.save()
            medInfo3.save()
            medInfo4.save()
            medInfo5.save()
            medInfo6.save()
            medInfo7.save()
            medInfo8.save()
            medInfo9.save()
            medInfo10.save()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        MedicineInfo.listAll(MedicineInfo::class.java)
    }
}
