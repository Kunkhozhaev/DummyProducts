package ru.nurdaulet.dummyproducts.presentation.screens.product

import android.content.res.Resources
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import ru.nurdaulet.dummyproducts.R
import ru.nurdaulet.dummyproducts.databinding.FragmentProductBinding
import ru.nurdaulet.dummyproducts.domain.models.ImageProduct
import ru.nurdaulet.dummyproducts.utils.roundTo2digits
import kotlin.math.abs

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding: FragmentProductBinding
        get() = _binding ?: throw RuntimeException("FragmentProductBinding == null")

    private val args by navArgs<ProductFragmentArgs>()

    private lateinit var vpAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupViewPager()

        val product = args.product
        binding.apply {
            tvProductName.text = product.title
            tvProductDescription.text = product.description
            tvRating.text = product.rating.toString()
            tvStock.text = getString(R.string.stock_number_format, product.stock.toString())
            val price = product.price
            val discount = product.discountPercentage
            val priceWithoutDiscount = (price * (1 - discount / 100)).roundTo2digits()
            tvPrice.text = getString(R.string.price_format, price.toString())
            tvPriceWithoutDiscount.text =
                getString(R.string.price_format, priceWithoutDiscount.toString())
            tvPriceWithoutDiscount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG or tvPrice.paintFlags
            tvDiscountPercent.text =
                getString(R.string.discount_percent_format, discount.toString())
        }

        val listOfImages = mutableListOf<ImageProduct>()
        product.images.forEach {
            listOfImages.add(ImageProduct(it))
        }
        vpAdapter.submitList(listOfImages.toList())

    }

    private fun setupViewPager() {
        vpAdapter = ViewPagerAdapter()
        binding.vp2.apply {
            adapter = vpAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
        }

        //Zoom-in and Zoom-out effect
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        binding.vp2.setPageTransformer(compositePageTransformer)
    }

    private fun setupMenu() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
