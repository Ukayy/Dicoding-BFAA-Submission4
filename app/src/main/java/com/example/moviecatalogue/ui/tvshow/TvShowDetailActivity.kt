package com.example.moviecatalogue.ui.tvshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.R
import com.example.moviecatalogue.adapter.SeasonAdapter
import com.example.moviecatalogue.helper.TvHelper
import com.example.moviecatalogue.model.Season
import com.example.moviecatalogue.model.TVShowDetail
import com.example.moviecatalogue.utils.Constant
import com.example.moviecatalogue.viewmodel.TvDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tv_show_detail.*
import kotlinx.android.synthetic.main.switch_online.*

class TvShowDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }

    private lateinit var viewModel: TvDetailViewModel
    private lateinit var adapter: SeasonAdapter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean? = null
    private lateinit var data: TVShowDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        showLoading(true)
        val tvId = intent.getStringExtra(EXTRA_TV_SHOW)

        supportActionBar?.title = "TV Show Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TvDetailViewModel::class.java)

        viewModel.setData(this, tvId).observe(this, Observer { t ->
            t.let {
                showLoading(false)
                if (it != null) {
                    data = it
                    init(it)
                }
            }
        })

        viewModel.stateIsFavorite().observe(this, Observer {
            if (it != null) {
                isFavorite = it
                setFavorite()
            }
        })
        btn_online.setOnClickListener {
            showLoading(true)
            viewModel.setDataFromServer(this, tvId).observe(this, Observer {
                v_online.visibility = View.GONE
                showLoading(false)
                if (it != null) {
                    data = it
                    init(it)
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            sm_detail_tv.startShimmer()
            sm_detail_tv.visibility = View.VISIBLE
        } else {
            sm_detail_tv.stopShimmer()
            sm_detail_tv.visibility = View.GONE
        }
    }

    private fun init(data: TVShowDetail) {
        supportActionBar?.title = data.name
        Picasso.get().load(Constant.URL_IMG + "w342" + data.backdrop).placeholder(R.drawable.movie)
            .into(img_tv_backdrop)
        Picasso.get().load(Constant.URL_IMG + "w92" + data.poster).placeholder(R.drawable.movie)
            .into(img_tv_poster)
        tv_tv_date.text = data.date
        tv_tv_overview.text = data.overview
        tv_tv_score.text = data.score
        tv_tv_title.text = data.name
        rv_season.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        if (data.season != null && data.season.isNotEmpty()) {
            adapter = SeasonAdapter(data.season as ArrayList<Season>)
            rv_season.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun setFavorite() {
        if (isFavorite == true) {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite)
            v_online.visibility = View.VISIBLE
        } else {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                data.let {
                    if (isFavorite == true) {
                        showAlertDialog()
                    } else {
                        viewModel.addFavorite(data)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAlertDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Hapus Favorite")
        alertDialog.setMessage("Apakah Anda ingin menghapus movie ini dari Favorite?..")
            .setPositiveButton("OK") { dialog, id ->
                viewModel.addFavorite(data)
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
        alertDialog.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        TvHelper.getInstance(this).close()
    }
}
