package com.example.test1.ui.myjokes.addjokedialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import com.example.test1.R
import com.example.test1.extensions.observe
import com.example.test1.ui.base.BaseDialogFragment
import com.example.test1.ui.myjokes.JokeSharedViewModel
import kotlinx.android.synthetic.main.fr_add_joke_fragment.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddJokeDialogFragment : BaseDialogFragment() {

    private val sharedViewModel by lazy {
        activity?.let {
            JokeSharedViewModel.obtainFrom(it)
        }
    }
    private val viewModel: AddJokeViewModel by stateViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_add_joke_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_joke_text.doAfterTextChanged { viewModel.textTextChanged(it.toString()) }
        btn_cancel.setOnClickListener {
            dismiss()
        }
        btn_save.setOnClickListener {
            viewModel.onSaveJokeClicked(et_joke_text.text.toString().trim())
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getIsEnabledSaveButton().observeEvent(this) {
            btn_save.isEnabled = it
        }
        viewModel.getIsDismiss().observeEvent(this) {
            sharedViewModel?.isAddedJoke?.postCall()
            dismiss()
        }
        viewModel.getJokeText().observe(this) {
            et_joke_text.setText(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

}