package ru.nurdaulet.dummyproducts.presentation.screens.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.nurdaulet.dummyproducts.R
import ru.nurdaulet.dummyproducts.domain.models.ImageProduct

class ViewPagerAdapter : ListAdapter<ImageProduct, ImageViewHolder>(ImagesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_product_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = currentList[position]
        holder.onBind(image)
    }
}