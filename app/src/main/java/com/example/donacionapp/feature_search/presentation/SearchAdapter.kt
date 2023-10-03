package com.example.donacionapp.feature_search.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.donacionapp.R
import com.example.donacionapp.databinding.DonorsItemBinding
import com.example.donacionapp.feature_donors.domain.Donors

class SearchAdapter(private var list: List<Donors>, private val onClicked: OnDonorClickeds):RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

    interface OnDonorClickeds{
        fun OnDonorClickListeners(donors: Donors, position: Int)
    }

    inner class ViewHolder(private val binding : DonorsItemBinding):RecyclerView.ViewHolder(binding.root){
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DonorsItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onClicked.OnDonorClickListeners(list[position], position)
        }
    }

    override fun getItemCount() = list.size
//    private fun getDonors(): Query {
//        return FirebaseFirestore.getInstance().collection("donantes")
//    }
//    @SuppressLint("NotifyDataSetChanged")
//    suspend fun filter(query: String?) {
//        if (query.isNullOrBlank()) {
//            list = getDonors().get().await().toObjects(Donors::class.java)
//        } else {
//            list = getDonors().whereEqualTo("blood", "$query").get().await().toObjects(Donors::class.java)
//        }
//        notifyDataSetChanged()
//    }
}
