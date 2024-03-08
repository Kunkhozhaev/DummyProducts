package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import androidx.recyclerview.widget.DiffUtil
import ru.nurdaulet.dummyproducts.domain.models.Product

class ProductsDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}