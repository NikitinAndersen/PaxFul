package com.example.test1.ui.myjokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.test1.R
import com.example.test1.extensions.observe
import com.example.test1.extensions.showToast
import com.example.test1.extensions.toDp
import com.example.test1.extensions.visible
import com.example.test1.ui.base.BaseFragment
import com.example.test1.ui.base.recycler.ItemSpacingDecoration
import kotlinx.android.synthetic.main.fragment_my_jokes.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class MyJokesFragment : BaseFragment() {

    private val viewModel: MyJokesViewModel by stateViewModel()
    private val sharedViewModel by lazy {
        activity?.let {
            JokeSharedViewModel.obtainFrom(it)
        }
    }
    private lateinit var adapter: MyJokesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_jokes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MyJokesAdapter(
            deleteClickListener = { viewModel.deleteJoke(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_jokes.adapter = adapter
        rv_jokes.addItemDecoration(ItemSpacingDecoration(toDp(10), 0, toDp(10)))

        fab_add.setOnClickListener {
            findNavController().navigate(MyJokesFragmentDirections.actionNavMyJokesToAddJokeDialogFragment())
        }
    }

    override fun observeViewModel() {
        viewModel.getIsLoading().observe(this) { showProgress ->
            progress.visible(showProgress)
        }
        viewModel.getIsError().observe(this) {
            it.message?.let { message ->
                context?.showToast(message)
            }
        }

        viewModel.getIsShowEmptyView().observe(this) {
            tv_empty_text.visible(it)
        }
        viewModel.jokes().observe(this) {
            adapter.items = it
        }
        sharedViewModel?.getIsAddedJoke()?.observeEvent(this) {
            viewModel.updateData()
        }
    }

}