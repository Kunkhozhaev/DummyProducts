package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.nurdaulet.dummyproducts.databinding.LayoutProductCardBinding
import ru.nurdaulet.dummyproducts.domain.models.Product

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = LayoutProductCardBinding.bind(itemView)

    fun onBind(
        product: Product
    ) {
        binding.apply {

        }

    }
}