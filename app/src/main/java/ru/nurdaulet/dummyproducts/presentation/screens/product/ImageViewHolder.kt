package ru.nurdaulet.dummyproducts.presentation.screens.product

import androidx.recyclerview.widget.RecyclerView
import ru.nurdaulet.dummyproducts.databinding.LayoutProductImageBinding
import ru.nurdaulet.dummyproducts.domain.models.ImageProduct
import ru.nurdaulet.dummyproducts.utils.loadWithGlide

class ImageViewHolder(private val binding: LayoutProductImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(imageProduct: ImageProduct) {
        binding.ivProduct.loadWithGlide(imageProduct.url)
    }
}