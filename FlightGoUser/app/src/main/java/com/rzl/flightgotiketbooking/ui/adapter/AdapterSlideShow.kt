package com.rzl.flightgotiketbooking.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.data.model.DataSlideShow

class AdapterSlideShow(val listSlideShow : ArrayList<DataSlideShow>)
    :RecyclerView.Adapter<AdapterSlideShow.ViewHolder>(){

    class ViewHolder(itemSlideShow: View): RecyclerView.ViewHolder(itemSlideShow){
        var imgSlideShow =itemSlideShow.findViewById<ImageView>(R.id.img_slide_show)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_slide_show, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgSlideShow.setImageResource(listSlideShow[position].imgSlide)
    }

    override fun getItemCount(): Int {
      return listSlideShow.size
    }
}
