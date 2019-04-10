package com.yevbes.movieland.view.ui


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yevbes.movieland.App
import com.yevbes.movieland.R
import com.yevbes.movieland.service.remote.State
import com.yevbes.movieland.service.remote.model.res.MoviesRes
import com.yevbes.movieland.view.adapter.MovieAdapter
import com.yevbes.movieland.viewmodel.TopRatedMovieViewModel
import kotlinx.android.synthetic.main.fragment_top_rated_movies.*


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
        val mLayoutManager = LinearLayoutManager(App.getApplication())
        rv.layoutManager = mLayoutManager
        rv.setHasFixedSize(true)

        topRatedMovieViewModel = ViewModelProviders.of(this).get(TopRatedMovieViewModel::class.java)
        val movieAdapter = MovieAdapter(topRatedMovieViewModel.retry())
        rv.adapter = movieAdapter

        topRatedMovieViewModel.getAllMovies().observe(this,
            Observer<PagedList<MoviesRes.Result>> { t ->
                movieAdapter.submitList(t)
//               movieAdapter.setMovies(t!!)
            })

        tv_error.setOnClickListener { topRatedMovieViewModel.retry() }
        topRatedMovieViewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility = if (topRatedMovieViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            tv_error.visibility = if (topRatedMovieViewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!topRatedMovieViewModel.listIsEmpty()) {
                movieAdapter.setState(state ?: State.DONE)
            }
        })
    }


}
