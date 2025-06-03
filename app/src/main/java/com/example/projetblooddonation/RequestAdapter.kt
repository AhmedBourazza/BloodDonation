package com.example.projetblooddonation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetblooddonation.RequestAdapter.RequetViewHolder

class RequestAdapter(private val context: Context, private val list: List<Request>) :
    RecyclerView.Adapter<RequetViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(request: Request?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequetViewHolder {
        return RequetViewHolder(
            LayoutInflater.from(context).inflate(R.layout.request_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RequetViewHolder, position: Int) {
        holder.content.text = list[position].content
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                val adapterPosition = holder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener!!.onItemClick(list[adapterPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class RequetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content: TextView =
            itemView.findViewById(R.id.content)
    }
}
