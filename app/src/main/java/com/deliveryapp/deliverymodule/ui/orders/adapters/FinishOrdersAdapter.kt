package com.deliveryapp.deliverymodule.ui.orders.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deliveryapp.deliverymodule.domain.model.Order
import com.deliveryapp.deliverymodule.ui.orders.ConfirmEventListener
import com.example.deliveryapp.databinding.ItemOrderBinding

class FinishOrdersAdapter(private val eventListener: ConfirmEventListener) :
    RecyclerView.Adapter<FinishOrdersAdapter.FinishOrdersViewHolder>() {

    var ordersList: List<Order> = emptyList()
        @SuppressLint("NotifyDataSetChanged") set(value) {
            field = value
            notifyDataSetChanged()
        }

    class FinishOrdersViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishOrdersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(inflater, parent, false)
        return FinishOrdersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    override fun onBindViewHolder(holder: FinishOrdersViewHolder, position: Int) {
        holder.binding.order = ordersList[position]
        holder.binding.showInMapBtn.visibility = View.GONE
        holder.binding.getOrderBtn.visibility = View.GONE
    }


}