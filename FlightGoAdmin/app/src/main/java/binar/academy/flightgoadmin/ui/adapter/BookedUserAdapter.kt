package binar.academy.flightgoadmin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.academy.flightgoadmin.databinding.ItemCustomerBinding
import binar.academy.flightgoadmin.data.model.booked.Data

class BookedUserAdapter(
    private val context: Context,
    private val onClick: BookedInterface,
    private var bookedResponse: List<Data>
): RecyclerView.Adapter<BookedUserAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemCustomerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCustomerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val currentList = bookedResponse[position]
            tvTransactionID.text = currentList.id.toString()
            tvDate.text = currentList.updatedAt
            tvUserId.text = currentList.userId.toString()
            ProductId.text = currentList.productId.toString()
        }

        holder.binding.root.setOnClickListener {
            onClick.onItemClick(bookedResponse[position])
        }
    }

    override fun getItemCount(): Int = bookedResponse.size

    interface BookedInterface {
        fun onItemClick(list : Data)
    }
}