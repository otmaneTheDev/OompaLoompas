package com.otmanethedev.oompaloopa.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.otmanethedev.oompaloopa.databinding.ItemOompaLoompaLoadStateBinding

class OompaLoompaLoadStateAdapter : LoadStateAdapter<OompaLoompaLoadStateAdapter.OompaLoompaLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: OompaLoompaLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): OompaLoompaLoadStateViewHolder {
        val binding = ItemOompaLoompaLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OompaLoompaLoadStateViewHolder(binding)
    }

    class OompaLoompaLoadStateViewHolder(private val binding: ItemOompaLoompaLoadStateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.txtMessage.text = loadState.error.localizedMessage
                binding.txtMessage.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }

            if (loadState is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.txtMessage.visibility = View.GONE
            }
        }
    }
}
