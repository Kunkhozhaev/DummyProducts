package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.nurdaulet.dummyproducts.databinding.LayoutProductCardBinding
import ru.nurdaulet.dummyproducts.domain.models.Product
import ru.nurdaulet.dummyproducts.utils.roundTo2digits

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = LayoutProductCardBinding.bind(itemView)

    fun onBind(
        product: Product,
        onProductClickListener: ((Product) -> Unit?)?
    ) {
        binding.apply {
            cardProduct.setOnClickListener {
                onProductClickListener?.invoke(product)
            }
            Glide.with(itemView)
                .load(product.thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(ivProduct)

            tvProductName.text = product.title
            tvProductDescription.text = product.description
            tvRating.text = product.rating.toString()
            tvStock.text = product.stock.toString()
            val price = product.price
            val discount = product.discountPercentage
            tvPrice.text = price.toString()
            tvDiscountPrice.text = (price * (1 - discount / 100)).roundTo2digits().toString()
            tvDiscountPercent.text = product.discountPercentage.toString()
        }
    }
}