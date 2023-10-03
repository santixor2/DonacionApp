package com.example.donacionapp.feature_request

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.donacionapp.databinding.RequestItemBinding
import com.example.donacionapp.feature_donors.domain.Donors
import com.example.donacionapp.util.TimeAgo

class RequestAdapter(private val list: List<Donors>) :
    RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: RequestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(donors: Donors) {
            binding.apply {
                textViewNameR.text = donors.name
                textViewHospital.text = donors.hospital
                textViewBlood.text = donors.blood
                val createAt = (donors.created_at?.time?.div(1000L))?.let {
                    TimeAgo.getTimeAgo(it.toInt())
                }
                textViewTime.text = createAt
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RequestItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestAdapter.ViewHolder, position: Int) {
        val list2 = list[position]
        holder.bind(list2)
    }

    override fun getItemCount() = list.size
}