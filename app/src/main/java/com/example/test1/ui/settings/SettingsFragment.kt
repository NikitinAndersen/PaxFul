package com.example.test1.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.test1.R
import com.example.test1.extensions.observe
import com.example.test1.extensions.showToast
import com.example.test1.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class SettingsFragment : BaseFragment() {

    private val viewModel: SettingsViewModel by stateViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switch_to_offline.setOnCheckedChangeListener { _, isChecked ->
            viewModel.offlineModeChecked(isChecked)
        }
        et_first_name.doAfterTextChanged { viewModel.firstNameChanged(it.toString().trim()) }
        et_last_name.doAfterTextChanged { viewModel.lastNameChanged(it.toString().trim()) }
    }

    override fun observeViewModel() {
        viewModel.getFirstName().observe(this) {
            et_first_name.setText(it)
        }
        viewModel.getLastName().observe(this) {
            et_last_name.setText(it)
        }
        viewModel.getIsOfflineModeEnabled().observe(this) {
            switch_to_offline.isChecked = it
        }
        viewModel.getIsError().observe(this) {
            it.message?.let {message->
                context?.showToast(message)
            }
        }
    }
}