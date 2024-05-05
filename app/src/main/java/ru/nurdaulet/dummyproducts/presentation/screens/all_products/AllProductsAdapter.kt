package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.nurdaulet.dummyproducts.databinding.LayoutProductCardBinding
import ru.nurdaulet.dummyproducts.domain.models.Product
import javax.inject.Inject

class AllProductsAdapter @Inject constructor() :
    ListAdapter<Product, ProductViewHolder>(ProductsDiffCallback()) {

    private var onProductClickListener: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(LayoutProductCardBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = currentList[position]
        holder.onBind(product, onProductClickListener)
    }

    fun setOnProductClickListener(listener: (Product) -> Unit) {
        onProductClickListener = listener
    }
}