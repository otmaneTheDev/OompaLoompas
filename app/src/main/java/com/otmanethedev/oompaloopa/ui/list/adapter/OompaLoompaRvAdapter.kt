package com.otmanethedev.oompaloopa.ui.list.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.otmanethedev.oompaloompa.info.domain.models.OompaLoompa
import com.otmanethedev.oompaloopa.R
import com.otmanethedev.oompaloopa.databinding.ItemOompaLoompaBinding

class OompaLoompaRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<OompaLoompa> = emptyList()
        set(value) {
            field = value
            filteredItems = value
        }

    var filteredItems = listOf<OompaLoompa>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: (OompaLoompa) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OompaLoompaViewHolder {
        val binding = ItemOompaLoompaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OompaLoompaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = filteredItems[position]
        (holder as OompaLoompaViewHolder).bind(item)
    }

    override fun getItemCount(): Int = filteredItems.size

    inner class OompaLoompaViewHolder(private val binding: ItemOompaLoompaBinding) : RecyclerView.ViewHolder(binding.root) {
        private val context by lazy { binding.root.context }

        fun bind(item: OompaLoompa) {
            binding.txtName.text = context.getString(R.string.oompaloompa_full_name, item.firstName, item.lastName)
            binding.txtId.text = context.getString(R.string.oompaloompa_id, item.id)
            configureImageAndBg(item.image)

            binding.root.setOnClickListener {
                itemClickListener(item)
            }
        }

        private fun configureImageAndBg(image: String?) {
            Glide.with(context).load(image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean = false

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        resource?.let {
                            runCatching {
                                val bgColor = it.toBitmap().getPixel(1, 1)
                                binding.bgContainer.backgroundTintList = ColorStateList.valueOf(bgColor)
                            }
                        }
                        return false
                    }
                }
                ).into(binding.imgAvatar)
        }
    }
}