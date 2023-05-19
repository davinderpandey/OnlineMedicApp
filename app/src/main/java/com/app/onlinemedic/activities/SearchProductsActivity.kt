package com.app.onlinemedic.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.onlinemedic.R
import com.app.onlinemedic.adapters.ProductsAdapter
import com.app.onlinemedic.commoners.AppUtils
import com.app.onlinemedic.commoners.BaseActivity
import com.app.onlinemedic.listeners.ItemClickListener
import com.app.onlinemedic.model.MedicineInfo
import com.app.onlinemedic.utils.PreferenceHelper
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.activity_search_product.*
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit

class SearchProductsActivity : BaseActivity(), ItemClickListener {
    private lateinit var products: MutableList<MedicineInfo>
    private var adapter: ProductsAdapter? = null
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_product)
        setSupportActionBar(toolbar)
        prefs = PreferenceHelper.defaultPrefs(this)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val heading = intent.getStringExtra("heading")
        if (heading != null)
            supportActionBar?.title = heading
        else
            supportActionBar?.title = "Search Product"

        setData()
        updateButton.setOnClickListener {
            Toast.makeText(
                this@SearchProductsActivity,
                "Products loaded successfully",
                Toast.LENGTH_SHORT
            ).show()
            updateButton.visibility = View.GONE
            adapter = ProductsAdapter(this, products, this)
            rvProducts?.adapter = adapter
        }

        RxTextView.textChanges(searchProduct).debounce(500, TimeUnit.MILLISECONDS)
            .subscribe {
                runOnUiThread {
                    if (searchProduct.text != null) {
                        if (searchProduct.text!!.trim().toString().isNotEmpty()) {
                            val fetchedResultList = mutableListOf<MedicineInfo>()
                            for (item in products) {
                                if (item.tab_name.toLowerCase(Locale.ROOT)
                                        .contains(searchProduct.text!!.trim().toString())
                                ) {
                                    fetchedResultList.add(item)
                                }
                            }
                            adapter?.updateList(fetchedResultList)
                            updateButton.visibility = View.VISIBLE
                        } else {
                            adapter?.updateList(products)
                            updateButton.visibility = View.GONE
                        }

                    }
                }
            }



    }

    private fun setData() {
        try {
            products = MedicineInfo.listAll(MedicineInfo::class.java)
            adapter = ProductsAdapter(this, mutableListOf(), this)
            val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            rvProducts?.layoutManager = manager
            rvProducts?.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(
                this@SearchProductsActivity,
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

    override fun onItemCLick(item: MedicineInfo?) {
        startActivity(Intent(this, ProductDetailActivity::class.java).putExtra("product", item))
        AppUtils.animateEnterRight(this@SearchProductsActivity)
    }
}