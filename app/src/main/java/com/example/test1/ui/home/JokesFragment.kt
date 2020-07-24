package com.example.test1.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.test1.R
import com.example.test1.ShakeDetectorUtil
import com.example.test1.extensions.*
import com.example.test1.ui.base.BaseFragment
import com.example.test1.ui.base.recycler.ItemSpacingDecoration
import com.squareup.seismic.ShakeDetector
import kotlinx.android.synthetic.main.fragment_jokes.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class JokesFragment : BaseFragment(), ShakeDetector.Listener {

    private val viewModel: JokesViewModel by stateViewModel()
    private val shakeDetector: ShakeDetectorUtil by inject()
    private lateinit var adapter: JokesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jokes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = JokesAdapter(
            likeClickListener = { viewModel.onLikeClicked(it) },
            shareClickListener = { viewModel.onShareClicked(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_jokes.adapter = adapter
        rv_jokes.addItemDecoration(ItemSpacingDecoration(toDp(10), 0, toDp(10)))

        swipe_refresh.setOnRefreshListener {
            viewModel.pullToRefresh()
        }
        shakeDetector.init( this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        shakeDetector.stop()
    }

    override fun observeViewModel() {
        viewModel.getIsLoading().observe(this) { showProgress ->
            progress.visible(showProgress)
            if (!showProgress) {
                swipe_refresh.isRefreshing = showProgress
            }
        }
        viewModel.jokes().observe(this) {
            adapter.items = it
        }
        viewModel.getRandomJoke().observe(this){
            adapter.items = listOf(it)
        }
        viewModel.getIsError().observe(this) {
            it.message?.let {message->

                context?.showToast(message)
            }
        }
        viewModel.getIsShowEmptyView().observe(this) {
            tv_empty_text.visible(it)
        }
        viewModel.getJokeToShare().observeEvent(this) {
            shareJokes(it)
        }
        viewModel.getJokeLiked().observeEvent(this) {
            context?.showToast(getString(R.string.joke_saved))
        }
        viewModel.getIsEnableShake().observe(this) {
            enableShake(it)
        }
    }

    private fun enableShake(enable: Boolean) {
        if (enable) {
            shakeDetector.start()
        } else {
            shakeDetector.stop()
        }
    }

    private fun shareJokes(joke: String) {
        context?.shareData(joke)
    }

    override fun hearShake() {
        viewModel.onDeviceShacked()
    }
}