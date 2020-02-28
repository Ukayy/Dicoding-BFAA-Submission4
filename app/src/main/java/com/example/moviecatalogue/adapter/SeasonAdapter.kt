package com.example.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.R
import com.example.moviecatalogue.model.Season
import com.example.moviecatalogue.utils.Constant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_season.view.*


class SeasonAdapter(private val seasons: ArrayList<Season>) :
    RecyclerView.Adapter<SeasonAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(season: Season) {
            with(itemView) {
                Picasso.get().load(Constant.URL_IMG + "w92/" + season.poster).into(img_season)
                tv_season.text = season.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_season, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = seasons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(seasons[position])
    }

}