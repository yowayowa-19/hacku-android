package com.yowayowa.yawning.ui.ranking

import android.view.View

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.yowayowa.yawning.R

class ComboViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var rankView: TextView
    var paramView: TextView
    var userNameView: TextView

    init {
        rankView = itemView.findViewById(R.id.rank)
        paramView = itemView.findViewById(R.id.param)
        userNameView = itemView.findViewById(R.id.userName)
    }
}
