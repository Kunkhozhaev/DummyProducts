package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.nurdaulet.dummyproducts.R
import ru.nurdaulet.dummyproducts.domain.models.Product

class AllProductsAdapter() : ListAdapter<Product, ProductViewHolder>(ProductsDiffCallback()) {

    private var onProductClickListener: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = currentList[position]
        holder.onBind(product, onProductClickListener)
    }

    fun setOnProductClickListener(listener: (Product) -> Unit) {
        onProductClickListener = listener
    }
}