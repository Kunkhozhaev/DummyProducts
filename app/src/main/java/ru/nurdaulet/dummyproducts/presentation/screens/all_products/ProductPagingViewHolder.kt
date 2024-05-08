package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import ru.nurdaulet.dummyproducts.R
import ru.nurdaulet.dummyproducts.databinding.LayoutProductCardBinding
import ru.nurdaulet.dummyproducts.domain.models.Product
import ru.nurdaulet.dummyproducts.utils.loadWithGlide
import ru.nurdaulet.dummyproducts.utils.roundTo2digits
import ru.nurdaulet.dummyproducts.utils.showToast

class ProductPagingViewHolder(private val binding: LayoutProductCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        product: Product,
        onProductClickListener: ((Product) -> Unit?)?
    ) {
        setViews(product)
        setClickListeners(product, onProductClickListener)
    }

    private fun setViews(product: Product) {
        binding.apply {
            ivProduct.loadWithGlide(product.thumbnail)
            tvProductName.text = product.title
            tvProductDescription.text = product.description
            tvRating.text = product.rating.toString()
            tvStock.text =
                itemView.context.getString(R.string.stock_number_format, product.stock.toString())

            val price = product.price
            tvPrice.text = itemView.context.getString(R.string.price_format, price.toString())

            val discount = product.discountPercentage
            tvDiscountPercent.text =
                itemView.context.getString(R.string.discount_percent_format, discount.toString())

            val priceWithoutDiscount = (price * (1 - discount / 100)).roundTo2digits()
            tvPriceWithoutDiscount.text =
                itemView.context.getString(R.string.price_format, priceWithoutDiscount.toString())
            tvPriceWithoutDiscount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG or tvPrice.paintFlags
        }
    }

    private fun setClickListeners(
        product: Product,
        onProductClickListener: ((Product) -> Unit?)?
    ) {
        binding.cardProduct.setOnClickListener { onProductClickListener?.invoke(product) }
        binding.btnBuy.setOnClickListener {
            itemView.context.apply { showToast(getString(R.string.buy_clicked)) }
        }
    }
}