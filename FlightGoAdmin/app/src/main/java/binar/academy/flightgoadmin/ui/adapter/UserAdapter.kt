package binar.academy.flightgoadmin.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.academy.flightgoadmin.databinding.CardUserBinding
import binar.academy.flightgoadmin.data.model.admin.Data

class UserAdapter(private var listUser: List<Data>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(var binding: CardUserBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}