package com.deliveryapp.ui.orders.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deliveryapp.domain.Order
import com.deliveryapp.domain.createGlideLink
import com.deliveryapp.ui.orders.ConfirmEventListener
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.BottomSheetOrderDataBinding
import com.example.deliveryapp.databinding.ItemOrderBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CurrentOrdersAdapter(private val eventListener: ConfirmEventListener) :
    RecyclerView.Adapter<CurrentOrdersAdapter.CurrentOrdersViewHolder>() {

    var ordersList: List<Order> = emptyList()
        @SuppressLint("NotifyDataSetChanged") set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CurrentOrdersViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentOrdersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(inflater, parent, false)
        return CurrentOrdersViewHolder(binding)
    }

    private fun confirmOrder(context: Context, item: Order) {
        val tv = TextView(context)
        tv.textSize = 16f

        showConfirmOrderDialog(item, context)
    }

    private fun showConfirmOrderDialog(item: Order, context: Context) {
        MaterialAlertDialogBuilder(context).setTitle("Вы уверены что выполнили заказ?")
            .setPositiveButton("Да") { _, _ ->
                eventListener.onFinishOrderBtnClicked(item)
            }.setNegativeButton("Нет") { _, _ ->
            }.show()
    }

    private fun showMap(inflater: LayoutInflater, context: Context, item: Order) {
        val view = inflater.inflate(R.layout.dialog_show_image, null)

        MaterialAlertDialogBuilder(context).setTitle("Расположение заказа на карте").setView(view)
            .setPositiveButton("Ок") { _, _ ->

            }

            .show()

        Glide.with(context).load(createGlideLink(item.point.latitude, item.point.longitude))
            .into(view.findViewById(R.id.dialogMapIV))
    }

    override fun getItemCount() = ordersList.size

    override fun onBindViewHolder(holder: CurrentOrdersViewHolder, position: Int) {
        val orderItem = ordersList[position]
        val inflater = LayoutInflater.from(holder.itemView.context)

        with(holder.binding) {
            order = orderItem
            root.setOnClickListener {
                showBottomSheetDialog(holder, inflater, orderItem)
            }
            showInMapBtn.setOnClickListener {
                showMap(inflater, holder.itemView.context, orderItem)
            }

            getOrderBtn.setOnClickListener {
                confirmOrder(holder.itemView.context, orderItem)
            }
            getOrderBtn.text = "Заказ выполнен"
        }
    }

    private fun showBottomSheetDialog(
        holder: CurrentOrdersViewHolder, inflater: LayoutInflater, orderItem: Order
    ) {
        val dialog = BottomSheetDialog(holder.itemView.context)
        val bottomSheetBinding = BottomSheetOrderDataBinding.inflate(inflater, null, false)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()

        bottomSheetBinding.order = orderItem
        bottomSheetBinding.getOrderBtmSheetBtn.text = "Заказ выполнен"
        bottomSheetBinding.getOrderBtmSheetBtn.setOnClickListener {
            showConfirmOrderDialog(orderItem, holder.itemView.context)
        }
        Glide.with(holder.itemView.context)
            .load(createGlideLink(orderItem.point.latitude, orderItem.point.longitude))
            .into(bottomSheetBinding.mapIV)
    }
}