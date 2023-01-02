package com.rzl.flightgotiketbooking.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.data.model.DataFlight


class AdapterFlight(val listFlight : ArrayList<DataFlight>)
    : RecyclerView.Adapter<AdapterFlight.ViewHolder>(){

    class ViewHolder(itemFlight: View): RecyclerView.ViewHolder(itemFlight){
        var price = itemFlight.findViewById<TextView>(R.id.tv_price)
        var imgFlight =itemFlight.findViewById<ImageView>(R.id.logo_maskapai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_flights, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = listFlight[position].price
        holder.imgFlight.setImageResource(listFlight[position].imgFlight)
    }

    override fun getItemCount(): Int {
        return listFlight.size
    }
}