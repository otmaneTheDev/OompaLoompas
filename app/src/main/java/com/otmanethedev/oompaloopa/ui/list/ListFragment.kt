package com.otmanethedev.oompaloopa.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import com.otmanethedev.oompaloopa.R
import com.otmanethedev.oompaloopa.databinding.FragmentListBinding
import com.otmanethedev.oompaloopa.ui.MainViewModel
import com.otmanethedev.oompaloopa.ui.filter.FilterConfig
import com.otmanethedev.oompaloopa.utils.BaseFragment
import com.otmanethedev.oompaloopa.ui.list.adapter.OompaLoompaRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel: ListViewModel by viewModels()

    private val rvAdapter by lazy { OompaLoompaRvAdapter() }
    private var previousFilterConfig: FilterConfig? = null

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, false)
    }

    override fun setUpUi() {
        binding.rvOompaLoompas.adapter = rvAdapter

        rvAdapter.itemClickListener = {
            navigate(ListFragmentDirections.actionListFragmentToDetailFragment(it))
        }

        binding.btnFilter.setOnClickListener {
            val genders = rvAdapter.items.mapNotNull { it.gender }.distinct()
            val professions = rvAdapter.items.mapNotNull { it.profession }.distinct()

            val filtersConfig = FilterConfig(
                genders,
                professions,
                previousFilterConfig?.previousSelectedGenders,
                previousFilterConfig?.previousSelectedProfessions
            )
            navigate(ListFragmentDirections.actionListFragmentToFilterFragment(filtersConfig))
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.filterEvent.collect {
                    when (it) {
                        MainViewModel.FilterEvent.Idle -> Unit
                        is MainViewModel.FilterEvent.Filter -> filterList(it.filterConfig)
                    }
                }
            }
        }
    }

    private fun filterList(filterConfig: FilterConfig) {
        rvAdapter.filteredItems = rvAdapter.items.filter {
            filterByGenderAndProfession(it, filterConfig)
        }
        previousFilterConfig = filterConfig
        Log.e("XXXX", "filterList: $previousFilterConfig", );
    }

    private fun filterByGenderAndProfession(item: OompaLoompa, filterConfig: FilterConfig): Boolean {
        val isInProfessions = item.profession in filterConfig.professionOptions || filterConfig.professionOptions.isEmpty()
        val isInGender = item.gender in filterConfig.genderOptions || filterConfig.genderOptions.isEmpty()

        return isInGender && isInProfessions
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
