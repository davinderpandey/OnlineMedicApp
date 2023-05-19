package com.app.onlinemedic.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.onlinemedic.R
import com.app.onlinemedic.listeners.ItemClickListener
import com.app.onlinemedic.model.MedicineInfo
import kotlinx.android.synthetic.main.item_product.view.*

class ProductsAdapter(
    var context: Context,
    private var mList: List<MedicineInfo>,
    private var listener: ItemClickListener
) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position], listener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateList(fetchedResultList: MutableList<MedicineInfo>) {
        this.mList = fetchedResultList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: MedicineInfo,
            listener: ItemClickListener
        ) {
            itemView.tvProduct?.text = item.tab_name
            itemView.tvProductQuantity?.text = item.quantity
            itemView.layoutContainer?.setOnClickListener {
                listener.onItemCLick(item)
            }
        }
    }
}