package com.example.adex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.adex.data.Offer
import com.example.adex.databinding.OfferListBinding


class OfferAdapter : ListAdapter<Offer, OfferAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = OfferListBinding.bind(view)
        fun bind(offer: Offer) = with(binding){

                offerDescription.text = offer.description
                if (offer.positive_responce == true){
                    offerResult.text = "Принято"
                }else if (offer.negative_responce == true){
                    offerResult.text = "Отклонено"
                } else offerResult.text = "На рассмотрении"
        }
    }

    class Comparator : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.offer_list, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}