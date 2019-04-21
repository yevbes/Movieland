package com.yevbes.movieland.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.*
import com.yevbes.movieland.R
import com.yevbes.movieland.databinding.FragmentTopRatedMoviesBinding
import com.yevbes.movieland.service.Movie
import com.yevbes.movieland.view.adapter.MovieAdapter
import com.yevbes.movieland.viewmodel.TopRatedMovieViewModel
import kotlinx.android.synthetic.main.fragment_top_rated_movies.*

/**
 * Top Rated Movies Fragment
 */
class TopRatedMoviesFragment : Fragment() {
    private lateinit var topRatedMovieViewModel: TopRatedMovieViewModel
    private lateinit var binding: FragmentTopRatedMoviesBinding

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopRatedMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view.setHasFixedSize(true)

        recycler_view.layoutManager = GridLayoutManager(
            activity, resources.getInteger(R.integer.grid_layout)
        )

        topRatedMovieViewModel = ViewModelProviders.of(this).get(TopRatedMovieViewModel::class.java)
        binding.viewModel = topRatedMovieViewModel

        recycler_view.adapter = movieAdapter

        // Observer for movies list
        /*topRatedMovieViewModel.getAllMovies().observe(this,
            Observer<List<Movie>> { t ->
                movieAdapter.setMovieItems(t!!)
            })*/
        topRatedMovieViewModel.getPagedListLiveData().observe(this,
            Observer<PagedList<Movie>> {
                movieAdapter.submitList(it)
            })

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_top_rated_movies,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
