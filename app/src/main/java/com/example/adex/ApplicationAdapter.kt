package com.example.adex

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.adex.data.Application
import com.example.adex.databinding.LapeListBinding


class ApplicationAdapter : ListAdapter<Application, ApplicationAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = LapeListBinding.bind(view)
        fun bind(application: Application) = with(binding){
            applicationTitle.text = application.title
            applicationSubscribes.text = application.subscribes.toString()
            applicationEr.text = application.er.toString()
            applicationRating.text = application.rating.toString()
            applicationViews.text = application.views.toString()
        }
    }

    class Comparator : DiffUtil.ItemCallback<Application>() {
        override fun areItemsTheSame(oldItem: Application, newItem: Application): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Application, newItem: Application): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lape_list, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener { v ->
            val intent = Intent(v.context, ProposalActivity::class.java)
            intent.putExtra("titleApplicationRecipient", getItem(position).titleApplicationRecipient)
            intent.putExtra("applicationId", getItem(position).id)
            intent.putExtra("currentUserName", getItem(position).currentUserName)
            intent.putExtra("titleApplication", getItem(position).title)
            intent.putExtra("currentUserId", getItem(position).currentUserId)
            intent.putExtra("currentUserLogin", getItem(position).currentUserLogin)
            v.context.startActivity(intent)
        }
        holder.bind(getItem(position))
    }


}