package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import ru.nurdaulet.dummyproducts.databinding.LayoutProductCardBinding
import ru.nurdaulet.dummyproducts.domain.models.Product
import javax.inject.Inject

class ProductsPagingAdapter @Inject constructor() :
    PagingDataAdapter<Product, ProductPagingViewHolder>(ProductPagingDiffCallback()) {

    private var onProductClickListener: ((Product) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductPagingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutProductCardBinding.inflate(inflater)
        return ProductPagingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductPagingViewHolder,
        position: Int
    ) {
        val product = getItem(position)
        product?.let { holder.onBind(it, onProductClickListener) }
    }

    fun setOnProductClickListener(listener: (Product) -> Unit) {
        onProductClickListener = listener
    }
}