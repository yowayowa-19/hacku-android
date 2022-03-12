package com.yowayowa.yawning.ui.ranking

import android.graphics.Typeface
import android.os.Bundle
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
        holder.paramView.text = "$distance"
        holder.userNameView.text = list.get(position).user_name
        // 1位の強調
        if (position == 0){
            holder.rankView.typeface = Typeface.DEFAULT_BOLD
            holder.rankView.textSize = 28f
            holder.paramView.typeface = Typeface.DEFAULT_BOLD
            holder.paramView.textSize = 28f
            holder.userNameView.typeface = Typeface.DEFAULT_BOLD
            holder.userNameView.textSize = 28f
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}
