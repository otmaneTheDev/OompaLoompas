package com.otmanethedev.oompaloopa.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import com.otmanethedev.oompaloopa.R
import com.otmanethedev.oompaloopa.databinding.FragmentDetailBinding
import com.otmanethedev.oompaloopa.utils.BaseFragment
import com.otmanethedev.oompaloopa.ui.detail.DetailViewModel.DetailEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel: DetailViewModel by viewModels()

    private val oompaLoompa by lazy {
        val args by navArgs<DetailFragmentArgs>()
        args.oompaLoompa
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.handleEvent(DetailEvent.GetById(oompaLoompa.id))
    }

    override fun setUpUi() {
        binding.btnBack.setOnClickListener { navigateUp() }

        binding.txtName.text = getString(R.string.oompaloompa_full_name, oompaLoompa.firstName, oompaLoompa.lastName)
        binding.txtId.text = getString(R.string.oompaloompa_id, oompaLoompa.id)
        binding.txtDescription.text = oompaLoompa.description
        binding.txtProfession.text = oompaLoompa.profession
        binding.txtAge.text = oompaLoompa.age.toString()
        binding.txtEmail.text = oompaLoompa.email

        configureImageAndBg(oompaLoompa.image)
    }

    override fun setUpObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        DetailViewModel.DetailUiState.Idle -> Unit
                        DetailViewModel.DetailUiState.Loading -> handeLoading()
                        is DetailViewModel.DetailUiState.Error -> handleError(it.error)
                        is DetailViewModel.DetailUiState.Success -> handleSuccess(it.oompaLoompa)
                    }
                }
            }
        }
    }

    private fun handeLoading() {

    }

    private fun handleSuccess(oompaLoompa: OompaLoompa) {
        binding.txtDescription.text = Html.fromHtml(oompaLoompa.description, Html.FROM_HTML_MODE_LEGACY)
    }

    private fun handleError(error: Throwable) {
        Toast.makeText(requireContext(), getString(R.string.error_unable_to_load_details), Toast.LENGTH_SHORT).show()
    }

    private fun configureImageAndBg(image: String?) {
        Glide.with(this).load(image)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean = false

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    resource?.let {
                        runCatching {
                            val bgColor = it.toBitmap().getPixel(1, 1)
                            binding.bgHeader.setBackgroundColor(bgColor)
                        }
                    }
                    return false
                }
            }
            ).into(binding.imgAvatar)
    }
}
