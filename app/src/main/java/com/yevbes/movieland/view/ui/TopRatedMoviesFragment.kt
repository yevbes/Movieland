package com.yevbes.movieland.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.*
import com.yevbes.movieland.R
import com.yevbes.movieland.databinding.FragmentTopRatedMoviesBinding
import com.yevbes.movieland.service.Movie
import com.yevbes.movieland.service.remote.State
import com.yevbes.movieland.view.adapter.MovieAdapter
import com.yevbes.movieland.viewmodel.TopRatedMovieViewModel
import kotlinx.android.synthetic.main.fragment_top_rated_movies.*

/**
 * A simple [Fragment] subclass.
 *
 */
class TopRatedMoviesFragment : Fragment() {
    private lateinit var topRatedMovieViewModel: TopRatedMovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: FragmentTopRatedMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_rated_movies, container, false)
//        return inflater.inflate(R.layout.fragment_top_rated_movies, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_view.layoutManager = GridLayoutManager(
            activity, resources.getInteger(R.integer.grid_layout)
        )
        recycler_view.setHasFixedSize(true)

        topRatedMovieViewModel = ViewModelProviders.of(this).get(TopRatedMovieViewModel::class.java)
        binding.viewModel = topRatedMovieViewModel

        movieAdapter = MovieAdapter { topRatedMovieViewModel.retry() }
        recycler_view.adapter = movieAdapter

        topRatedMovieViewModel.getAllMovies().observe(this,
            Observer<PagedList<Movie>> { t ->
                movieAdapter.submitList(t)
//               movieAdapter.setMovies(t!!)
            })

        tv_error.setOnClickListener { topRatedMovieViewModel.retry() }
        topRatedMovieViewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility =
                if (topRatedMovieViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            tv_error.visibility =
                if (topRatedMovieViewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!topRatedMovieViewModel.listIsEmpty()) {
                movieAdapter.setState(state ?: State.DONE)
            }

            topRatedMovieViewModel.isLoadingLive().observe(this, Observer { t ->
                if (t!!) {
                    if (state == State.DONE)
                        topRatedMovieViewModel.onReady()
                    if (state == State.ERROR)
                        topRatedMovieViewModel.onError()
                }
            })
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_top_rated_movies,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
