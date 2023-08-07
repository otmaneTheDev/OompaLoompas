package com.otmanethedev.oompaloopa.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.otmanethedev.oompaloopa.databinding.FragmentListBinding
import com.otmanethedev.oompaloopa.ui.MainViewModel
import com.otmanethedev.oompaloopa.ui.filter.FilterConfig
import com.otmanethedev.oompaloopa.ui.list.adapter.OompaLoompaAdapter
import com.otmanethedev.oompaloopa.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel: ListViewModel by viewModels()

    private val adapter by lazy { OompaLoompaAdapter() }
    private var previousFilterConfig: FilterConfig? = null

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, false)
    }

    override fun setUpUi() {
        binding.rvOompaLoompas.adapter = adapter
        adapter.itemClickListener = {
            navigate(ListFragmentDirections.actionListFragmentToDetailFragment(it))
        }

        binding.btnFilter.setOnClickListener {
            val filtersConfig = FilterConfig(
                possibleGenders,
                possibleProfessions,
                previousFilterConfig?.previousSelectedGenders,
                previousFilterConfig?.previousSelectedProfessions
            )
            navigate(ListFragmentDirections.actionListFragmentToFilterFragment(filtersConfig))
        }
    }

    override fun setUpObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.filterEvent.collectLatest {
                    when (it) {
                        MainViewModel.FilterEvent.Idle -> Unit
                        is MainViewModel.FilterEvent.Filter -> filterList(it.filterConfig)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filteredOompaLoompas.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun filterList(filterConfig: FilterConfig) {
        viewModel.applyFilterConfig(filterConfig)
        previousFilterConfig = filterConfig
    }

    companion object {
        private val possibleGenders = listOf("F", "M")
        private val possibleProfessions = listOf("Brewer", "Metalworker", "Developer", "Gemcutter", "Medic")
    }
}
