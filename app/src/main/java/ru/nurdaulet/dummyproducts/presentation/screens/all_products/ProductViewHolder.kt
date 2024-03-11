package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.app.Application
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.nurdaulet.dummyproducts.R
import ru.nurdaulet.dummyproducts.databinding.LayoutProductCardBinding
import ru.nurdaulet.dummyproducts.domain.models.Product
import ru.nurdaulet.dummyproducts.utils.roundTo2digits

class ProductViewHolder(itemView: View, private val application: Application) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = LayoutProductCardBinding.bind(itemView)

    fun onBind(
        product: Product,
        onProductClickListener: ((Product) -> Unit?)?
    ) {
        binding.apply {
            cardProduct.setOnClickListener {
                onProductClickListener?.invoke(product)
            }
            //TODO Add error placeholder
            Glide.with(itemView)
                .load(product.thumbnail)
//                .placeholder()
//                .error()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(ivProduct)

            tvProductName.text = product.title
            tvProductDescription.text = product.description
            tvRating.text = product.rating.toString()
            tvStock.text = product.stock.toString()
            val price = product.price
            val discount = product.discountPercentage
            val priceWithoutDiscount = (price * (1 - discount / 100)).roundTo2digits()
            tvPrice.text = application.getString(R.string.price_format, price.toString())
            tvPriceWithoutDiscount.text =
                application.getString(R.string.price_format, priceWithoutDiscount.toString())
            tvPriceWithoutDiscount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG or tvPrice.paintFlags
            tvDiscountPercent.text =
                application.getString(R.string.discount_percent_format, discount.toString())
        }
    }
}