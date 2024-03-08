package ru.nurdaulet.dummyproducts.presentation.screens.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.nurdaulet.dummyproducts.databinding.LayoutProductImageBinding
import ru.nurdaulet.dummyproducts.domain.models.ImageProduct

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = LayoutProductImageBinding.bind(itemView)

    fun onBind(
        imageProduct: ImageProduct,
    ) {
        binding.apply {
            Glide.with(itemView)
                .load(imageProduct.url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(ivProduct)
        }
    }
}