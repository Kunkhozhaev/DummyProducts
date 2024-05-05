package ru.nurdaulet.dummyproducts.presentation.screens.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.nurdaulet.dummyproducts.databinding.LayoutProductImageBinding
import ru.nurdaulet.dummyproducts.domain.models.ImageProduct

class ViewPagerAdapter : ListAdapter<ImageProduct, ImageViewHolder>(ImagesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(LayoutProductImageBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = currentList[position]
        holder.onBind(image)
    }
}