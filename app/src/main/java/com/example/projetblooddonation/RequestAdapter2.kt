package com.example.projetblooddonation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetblooddonation.RequestAdapter2.RequestViewHolder


class RequestAdapter2(private val context: Context, private var list: List<Request>) :
    RecyclerView.Adapter<RequestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        return RequestViewHolder(
            LayoutInflater.from(context).inflate(R.layout.global_request_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.contenu.text = list[position].content
        holder.email.text = list[position].email
        holder.city.text = list[position].city
        holder.blood.text = list[position].blood_type
        holder.tel.text = list[position].tel
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun FilterList(filterList: ArrayList<Request>) {
        list = filterList
        notifyDataSetChanged()
    }

    inner class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var email: TextView =
            itemView.findViewById(R.id.email)
        var tel: TextView =
            itemView.findViewById(R.id.phone)
        var city: TextView =
            itemView.findViewById(R.id.city)
        var blood: TextView =
            itemView.findViewById(R.id.Bloodgrp)
        var contenu: TextView =
            itemView.findViewById(R.id.content)
    }
}