package ru.nurdaulet.dummyproducts.presentation.screens.product

import androidx.recyclerview.widget.DiffUtil
import ru.nurdaulet.dummyproducts.domain.models.ImageProduct

class ImagesDiffCallback : DiffUtil.ItemCallback<ImageProduct>() {
    override fun areItemsTheSame(oldItem: ImageProduct, newItem: ImageProduct): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: ImageProduct, newItem: ImageProduct): Boolean {
        return oldItem == newItem
    }
}