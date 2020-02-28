package com.example.moviecatalogue.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalogue.R
import com.example.moviecatalogue.helper.MovieHelper
import com.example.moviecatalogue.model.MovieDetail
import com.example.moviecatalogue.utils.Constant
import com.example.moviecatalogue.viewmodel.MovieDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.switch_online.*

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var viewModel: MovieDetailViewModel
    private var menuItem: Menu? = null
    private lateinit var data: MovieDetail
    private var isFavorite: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getStringExtra(EXTRA_MOVIE)

        supportActionBar?.title = "Movie Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MovieDetailViewModel::class.java)

        viewModel.setData(this, movieId).observe(this, Observer { t ->
            t.let {
                if (it != null) {
                    showLoading(false)
                    data = it
                    init(it)
                }
            }
        })

        viewModel.stateIsFavorite().observe(this, Observer {
            if (it != null) {
                Log.d("HH", "State isFavorite: $it")
                isFavorite = it
                setFavorite()
            }
        })

        btn_online.setOnClickListener {
            showLoading(true)
            viewModel.setDataFromServer(this, movieId).observe(this, Observer { t ->
                t.let {
                    showLoading(false)
                    v_online.visibility = View.GONE
                    if (it != null) {
                        data = it
                        init(it)
                    }
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
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
                if (isFavorite == true) {
                    showAlertDialog()
                } else if (isFavorite == false) {

                    viewModel.addFavorite(data)
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            sm_detail.startShimmer()
            sm_detail.visibility = View.VISIBLE
        } else {
            sm_detail.stopShimmer()
            sm_detail.visibility = View.GONE
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

    private fun init(data: MovieDetail) {
        supportActionBar?.title = data.title
        Picasso.get().load(Constant.URL_IMG + "w342" + data.backdrop).placeholder(R.drawable.movie)
            .into(img_m_backdrop)
        Picasso.get().load(Constant.URL_IMG + "w92" + data.poster).placeholder(R.drawable.movie)
            .into(img_m_poster)
        tv_m_title.text = data.title
        tv_m_date.text = data.date
        tv_m_score.text = data.score
        tv_m_tag_line.text = data.tagLine
        tv_m_overview.text = data.overview
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
        MovieHelper.getInstance(this).close()
    }
}
