package com.divyang.thesportsapp.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.divyang.thesportsapp.R


class TeamsAdapter(
    private val list: List<TeamMember>,
    private val itemClicked: (teamID: Int) -> Unit
) : RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {
    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.team_icon)
        val name: TextView = itemView.findViewById(R.id.team_name)
        val text1: TextView = itemView.findViewById(R.id.team_country)
        val text2: TextView = itemView.findViewById(R.id.team_sport)
        val favoriteTeam: ImageButton = itemView.findViewById(R.id.favorite_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = list[position]
        holder.name.text = team.name
        holder.text1.text = team.country
        holder.text2.text = team.sport

        holder.itemView.setOnClickListener { itemClicked.invoke(position) }

        if (team.iconURL == null) {
            Glide
                .with(holder.itemView)
                .load(R.drawable.ic_launcher_background)
                .into(holder.icon)
        } else {
            Glide
                .with(holder.itemView)
                .load("${team.iconURL}/preview")
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_people_outline_24)
                .into(holder.icon)
        }

        val prefs = holder.itemView.context.getSharedPreferences("SportDB", Context.MODE_PRIVATE)

        holder.favoriteTeam.setOnClickListener {
            if (prefs.getInt("${team.id}", 0) == team.id) {
                prefs.edit()
                    .putInt("${team.id}", 0)
                    .apply()
            } else {
                prefs.edit()
                    .putInt("${team.id}", team.id)
                    .apply()
            }

            notifyItemChanged(position)
        }

        if (prefs.getInt("${team.id}", 0) == team.id) {
            holder.favoriteTeam.setImageResource(R.drawable.ic_favorite_fill)
        } else {
            holder.favoriteTeam.setImageResource(R.drawable.ic_favorite)
        }
    }
}