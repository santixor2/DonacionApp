package com.example.donacionapp.feature_donors.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.donacionapp.R
import com.example.donacionapp.databinding.DonorsItemBinding
import com.example.donacionapp.feature_donors.domain.Donors

class DonorsAdapter(private val list: List<Donors>, private val onClickedDonors: OnDonorClicked) :
    RecyclerView.Adapter<DonorsAdapter.ViewHolder>() {

    private var savelist : ArrayList<Donors> = arrayListOf()

    init {
        savelist.addAll(list)
    }

    interface OnDonorClicked{
        fun OnDonorClickListener(donors: Donors, position: Int)
    }
    inner class ViewHolder(private val binding: DonorsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(donors: Donors) {
            binding.apply {
                textViewLocation.text = donors.city
                textViewTipeBlood.text = donors.blood
                textViewNameDonors.text = donors.name
                //Glide.with(root.context).load(donors.image).into(shapeableImageViewDonors)
                shapeableImageViewDonors.load(donors.image){
                    placeholder(R.drawable.error)
                    crossfade(true)
                    crossfade(300)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorsAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DonorsItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonorsAdapter.ViewHolder, position: Int) {
        //val list2 = list[position]
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onClickedDonors.OnDonorClickListener(list[position], position)
        }
    }

    override fun getItemCount() = list.size

//    @SuppressLint("NotifyDataSetChanged")
//    fun filtrar(q : String){
//        if(q.isEmpty()){
//            list.clear()
//            list.addAll(savelist)
//        }else{
//            val newList : ArrayList<Donors> = arrayListOf()
//            for (lists in list){
//                if(lists.name.lowercase().contains(q.lowercase()))
//                    newList.add(lists)
//            }
//            list.clear()
//            list.addAll(newList)
//        }
//        notifyDataSetChanged()
//    }
}