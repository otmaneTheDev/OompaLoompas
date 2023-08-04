package com.otmanethedev.oompaloopa.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import com.otmanethedev.oompaloopa.R
import com.otmanethedev.oompaloopa.databinding.FragmentListBinding
import com.otmanethedev.oompaloopa.utils.BaseFragment
import com.otmanethedev.oompaloopa.ui.list.adapter.OompaLoompaRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>() {

    private val viewModel: ListViewModel by viewModels()

    private val rvAdapter by lazy { OompaLoompaRvAdapter() }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, false)
    }

    override fun setUpUi() {
        binding.rvOompaLoompas.adapter = rvAdapter

        rvAdapter.itemClickListener = {
            navigate(ListFragmentDirections.actionListFragmentToDetailFragment(it))
        }
    }

    override fun setUpObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        ListViewModel.ListUiState.Idle -> Unit
                        ListViewModel.ListUiState.Loading -> handeLoading()
                        is ListViewModel.ListUiState.Error -> handleError(it.error)
                        is ListViewModel.ListUiState.Success -> handleSuccess(it.items)
                    }
                }
            }
        }
    }

    private fun handeLoading() {

    }

    private fun handleSuccess(items: List<OompaLoompa>) {
        rvAdapter.items = items
    }

    private fun handleError(error: Throwable) {
        Toast.makeText(requireContext(), getString(R.string.error_unable_to_load_list), Toast.LENGTH_SHORT).show()
    }
}
