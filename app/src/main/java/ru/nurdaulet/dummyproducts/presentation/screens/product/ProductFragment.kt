package ru.nurdaulet.dummyproducts.presentation.screens.product

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import ru.nurdaulet.dummyproducts.databinding.FragmentProductBinding
import ru.nurdaulet.dummyproducts.domain.models.ImageProduct
import kotlin.math.abs

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding: FragmentProductBinding
        get() = _binding ?: throw RuntimeException("FragmentProductBinding == null")

    private lateinit var vpAdapter: ViewPagerAdapter

    private val listOfImages = listOf(
        ImageProduct("https://cdn.dummyjson.com/product-images/1/1.jpg"),
        ImageProduct("https://cdn.dummyjson.com/product-images/1/2.jpg"),
        ImageProduct("https://cdn.dummyjson.com/product-images/1/3.jpg"),
        ImageProduct("https://cdn.dummyjson.com/product-images/1/4.jpg"),
        ImageProduct("https://cdn.dummyjson.com/product-images/1/thumbnail.jpg"),
    )

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
