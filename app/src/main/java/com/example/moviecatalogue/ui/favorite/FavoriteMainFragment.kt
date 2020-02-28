package com.example.moviecatalogue.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.moviecatalogue.R
import com.example.moviecatalogue.adapter.TabAdapter
import kotlinx.android.synthetic.main.fragment_favorite_main.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        vp_favorite.adapter =
            TabAdapter((activity as AppCompatActivity).applicationContext, childFragmentManager)
        tab_favorite.setupWithViewPager(vp_favorite)
    }
}
