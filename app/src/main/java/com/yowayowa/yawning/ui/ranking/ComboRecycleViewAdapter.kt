package com.yowayowa.yawning.ui.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yowayowa.yawning.ComboRanking
import com.yowayowa.yawning.R

class ComboRecycleViewAdapter(list: List<ComboRanking>) : RecyclerView.Adapter<ComboViewHolder>() {
    private val list  = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComboViewHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.ranking_row,parent,false)
        return ComboViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ComboViewHolder, position: Int) {
        val rank = list.get(position).rank
        val combo = list.get(position).combo_count
        holder.rankView.text = "$rank 位"
        holder.paramView.text = "$combo Combo"
        holder.userNameView.text = list.get(position).user_name
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}
