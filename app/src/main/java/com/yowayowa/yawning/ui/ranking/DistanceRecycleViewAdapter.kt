package com.yowayowa.yawning.ui.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yowayowa.yawning.ComboRanking
import com.yowayowa.yawning.DistanceRanking
import com.yowayowa.yawning.R

class DistanceRecycleViewAdapter(list: List<DistanceRanking>) : RecyclerView.Adapter<RankingViewHolder>() {
    private val list  = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.ranking_row,parent,false)
        return RankingViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val rank = list.get(position).rank
        val distance = list.get(position).distance
        holder.rankView.text = "$rank 位"
        holder.paramView.text = "$distance km"
        holder.userNameView.text = list.get(position).user_name
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}
