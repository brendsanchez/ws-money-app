package com.dusk.ws_dollar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dusk.ws_dollar.databinding.ViewDollarItemBinding
import com.dusk.ws_dollar.dto.Price
import com.dusk.ws_dollar.utils.Util

class DollarsAdapter(private val dollars: List<Price>) :
    RecyclerView.Adapter<DollarsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewDollarItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dollars[position])
    }

    override fun getItemCount() = dollars.size

    class ViewHolder(private val binding: ViewDollarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dollar: Price) {
            binding.titleDollarTw.text = dollar.name
            "Buy: ${dollar.buy.valueText ?: "-"}".also { binding.priceBuyTw.text = it }
            "Sell: ${dollar.sell.valueText ?: "-"}".also { binding.priceSellTv.text = it }
            binding.timestampTv.text = Util.dateFormatted(dollar.date)
        }
    }
}