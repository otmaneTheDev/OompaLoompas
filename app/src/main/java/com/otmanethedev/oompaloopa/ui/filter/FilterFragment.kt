package com.otmanethedev.oompaloopa.ui.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.otmanethedev.oompaloopa.R
import com.otmanethedev.oompaloopa.databinding.FragmentFitersBinding
import com.otmanethedev.oompaloopa.ui.MainViewModel
import com.otmanethedev.oompaloopa.ui.MainViewModel.FilterEvent
import com.otmanethedev.oompaloopa.utils.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : BaseBottomSheetFragment<FragmentFitersBinding>() {

    private val mainViewModel by activityViewModels<MainViewModel>()

    private val filterConfig by lazy {
        val args by navArgs<FilterFragmentArgs>()
        args.filtersConfig
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFitersBinding {
        return FragmentFitersBinding.inflate(inflater, container, false)
    }

    override fun setUpUi() {
        setUpGenderOptions(filterConfig.genderOptions)
        setUpProfessionOptions(filterConfig.professionOptions)

        binding.btnClear.setOnClickListener {
            mainViewModel.handleFilterEvent(FilterEvent.Filter(clearFilters()))
            navigateUp()
        }

        binding.btnConfirm.setOnClickListener {
            mainViewModel.handleFilterEvent(FilterEvent.Filter(getSelectedFilters()))
            navigateUp()
        }

        binding.btnClose.setOnClickListener {
            navigateUp()
        }
    }

    private fun setUpGenderOptions(options: List<String>) {
        binding.optionsGender.removeAllViews()
        options.map {
            val chip = buildChip(requireContext(), it)
            chip.isChecked = filterConfig.previousSelectedGenders?.contains(it) == true
            binding.optionsGender.addView(chip)
        }
    }

    private fun setUpProfessionOptions(options: List<String>) {
        binding.optionsProfession.removeAllViews()
        options.map {
            val chip = buildChip(requireContext(), it)
            chip.isChecked = filterConfig.previousSelectedProfessions?.contains(it) == true
            binding.optionsProfession.addView(chip)
        }
    }

    private fun buildChip(context: Context, item: String): Chip {
        val chip = Chip(context).apply {
            setTextColor(ContextCompat.getColor(context, R.color.black))
            setChipBackgroundColorResource(android.R.color.transparent)
            setChipStrokeWidthResource(R.dimen.dimen_1)
            setChipStrokeColorResource(R.color.black)
            setEnsureMinTouchTargetSize(false)
            isClickable = true
            isCheckable = true
            text = item
        }
        return chip
    }

    private fun getSelectedFilters(): FilterConfig {
        val genderOptions = mutableListOf<String>()
        val professionOptions = mutableListOf<String>()

        for (id in binding.optionsGender.checkedChipIds) {
            val chip: Chip = binding.optionsGender.findViewById(id)
            genderOptions.add(chip.text.toString())
        }

        for (id in binding.optionsProfession.checkedChipIds) {
            val chip: Chip = binding.optionsProfession.findViewById(id)
            professionOptions.add(chip.text.toString())
        }

        return FilterConfig(genderOptions, professionOptions, genderOptions, professionOptions)
    }

    private fun clearFilters(): FilterConfig {
        return FilterConfig(listOf(), listOf(), listOf(), listOf())
    }
}