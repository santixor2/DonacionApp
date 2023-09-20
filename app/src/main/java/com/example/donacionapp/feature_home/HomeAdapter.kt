package com.example.donacionapp.feature_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.donacionapp.databinding.RequestItemBinding
import com.example.donacionapp.feature_donors.domain.Donors
import com.example.donacionapp.util.TimeAgo

class HomeAdapter(private val list : List<Donors>):RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: RequestItemBinding):
        RecyclerView.ViewHolder(binding.root){

            fun bind(donors: Donors){
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RequestItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        val list2 = list[position]
        holder.bind(list2)
    }

    override fun getItemCount()= 1
}