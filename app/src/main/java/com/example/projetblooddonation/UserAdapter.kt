package com.example.projetblooddonation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetblooddonation.UserAdapter.UserViewHolder

class UserAdapter(private val context: Context, private var list: List<User>) :
    RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(context).inflate(R.layout.user_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = list[position]

        holder.email.text = list[position].email
        holder.blood.text = list[position].bloodgrp
        holder.city.text = list[position].city
        holder.phone.text = list[position].phone
        val imageBase64 = user.imageBase64
        if (!imageBase64.isNullOrEmpty()) {
            try {
                val decodedBytes = android.util.Base64.decode(imageBase64, android.util.Base64.DEFAULT)
                val bitmap = android.graphics.BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                holder.image.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                // image invalide => image par défaut
                holder.image.setImageResource(R.drawable.default_user_image)
            }
        } else {
            // image manquante => image par défaut
            holder.image.setImageResource(R.drawable.default_user_image)
        }
    }

    fun FilterList(filterList: ArrayList<User>) {
        list = filterList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var email: TextView =
            itemView.findViewById(R.id.email)
        var blood: TextView =
            itemView.findViewById(R.id.Bloodgrp)
        var city: TextView =
            itemView.findViewById(R.id.city)
        var phone: TextView =
            itemView.findViewById(R.id.phone)
        val image: ImageView = itemView.findViewById(R.id.imageViewUser)  // Ajout de l'image

    }
}
