package com.example.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.R
import com.example.moviecatalogue.model.TVShow
import com.example.moviecatalogue.utils.Constant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tv_show.view.*


class TVShowAdapter(
    private val tvShows: ArrayList<TVShow>,
    private val listener: (TVShow) -> Unit
) :
    RecyclerView.Adapter<TVShowAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(tvShow: TVShow, listener: (TVShow) -> Unit) {
            with(itemView) {
                tvShow.poster?.let { Picasso.get().load(Constant.URL_IMG + "w92/"+ it).into(img_tv_show) }
                tv_title_tv_show.text = tvShow.title
                tv_score_tv_show.text = tvShow.score
                tv_overview_tv_show.text = tvShow.overview
                tv_date_tv_show.text = tvShow.date
            }
            itemView.setOnClickListener {
                listener(tvShow)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(holder: TVShowAdapter.ViewHolder, position: Int) {
        holder.bindItem(tvShows[position], listener)
    }

}