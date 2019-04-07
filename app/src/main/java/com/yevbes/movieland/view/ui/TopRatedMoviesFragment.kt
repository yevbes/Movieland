package com.yevbes.movieland.view.ui


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yevbes.movieland.App

import com.yevbes.movieland.R
import com.yevbes.movieland.model.res.MoviesRes
import com.yevbes.movieland.view.adapter.MovieAdapter
import com.yevbes.movieland.viewmodel.TopRatedMovieViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class TopRatedMoviesFragment : Fragment() {
    private lateinit var topRatedMovieViewModel: TopRatedMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_top_rated_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val rv = view!!.findViewById<RecyclerView>(R.id.recycler_view)
        rv.layoutManager = LinearLayoutManager(App.getApplication())
        rv.setHasFixedSize(true)

        val movieAdapter = MovieAdapter()
        rv.adapter = movieAdapter

        topRatedMovieViewModel = ViewModelProviders.of(this).get(TopRatedMovieViewModel::class.java)
        topRatedMovieViewModel.getAllMovies().observe(this,
            Observer<List<MoviesRes.Result>> {
                    t -> movieAdapter.setMovies(t!!)
            })
    }


}
