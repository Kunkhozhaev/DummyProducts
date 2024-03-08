package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ru.nurdaulet.dummyproducts.databinding.FragmentAllProductsBinding
import ru.nurdaulet.dummyproducts.domain.models.Product

class AllProductsFragment : Fragment() {

    private var _binding: FragmentAllProductsBinding? = null
    private val binding: FragmentAllProductsBinding
        get() = _binding ?: throw RuntimeException("FragmentAllProductsBinding == null")

    private lateinit var productsAdapter: AllProductsAdapter
    private val productsList = listOf(
        Product(
            1,
            "iPhone 9",
            "An apple mobile which is nothing like apple",
            549,
            12.96,
            4.69,
            94,
            "Apple",
            "smartphones",
            "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
            listOf(
                "https://cdn.dummyjson.com/product-images/1/1.jpg",
                "https://cdn.dummyjson.com/product-images/1/2.jpg",
                "https://cdn.dummyjson.com/product-images/1/3.jpg",
                "https://cdn.dummyjson.com/product-images/1/4.jpg",
                "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg"
            )
        ),
        Product(
            2,
            "iPhone 9",
            "An apple mobile which is nothing like apple",
            549,
            12.96,
            4.69,
            94,
            "Apple",
            "smartphones",
            "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
            listOf(
                "https://cdn.dummyjson.com/product-images/1/1.jpg",
                "https://cdn.dummyjson.com/product-images/1/2.jpg",
                "https://cdn.dummyjson.com/product-images/1/3.jpg",
                "https://cdn.dummyjson.com/product-images/1/4.jpg",
                "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg"
            )
        ),
        Product(
            3,
            "iPhone 9",
            "An apple mobile which is nothing like apple",
            549,
            12.96,
            4.69,
            94,
            "Apple",
            "smartphones",
            "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
            listOf(
                "https://cdn.dummyjson.com/product-images/1/1.jpg",
                "https://cdn.dummyjson.com/product-images/1/2.jpg",
                "https://cdn.dummyjson.com/product-images/1/3.jpg",
                "https://cdn.dummyjson.com/product-images/1/4.jpg",
                "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg"
            )
        ),
        Product(
            4,
            "iPhone 9",
            "An apple mobile which is nothing like apple",
            549,
            12.96,
            4.69,
            94,
            "Apple",
            "smartphones",
            "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
            listOf(
                "https://cdn.dummyjson.com/product-images/1/1.jpg",
                "https://cdn.dummyjson.com/product-images/1/2.jpg",
                "https://cdn.dummyjson.com/product-images/1/3.jpg",
                "https://cdn.dummyjson.com/product-images/1/4.jpg",
                "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg"
            )
        ),
        Product(
            5,
            "iPhone 9",
            "An apple mobile which is nothing like apple",
            549,
            12.96,
            4.69,
            94,
            "Apple",
            "smartphones",
            "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
            listOf(
                "https://cdn.dummyjson.com/product-images/1/1.jpg",
                "https://cdn.dummyjson.com/product-images/1/2.jpg",
                "https://cdn.dummyjson.com/product-images/1/3.jpg",
                "https://cdn.dummyjson.com/product-images/1/4.jpg",
                "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg"
            )
        ),
        Product(
            6,
            "iPhone 9",
            "An apple mobile which is nothing like apple",
            549,
            12.96,
            4.69,
            94,
            "Apple",
            "smartphones",
            "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
            listOf(
                "https://cdn.dummyjson.com/product-images/1/1.jpg",
                "https://cdn.dummyjson.com/product-images/1/2.jpg",
                "https://cdn.dummyjson.com/product-images/1/3.jpg",
                "https://cdn.dummyjson.com/product-images/1/4.jpg",
                "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg"
            )
        ),
        Product(
            7,
            "iPhone 9",
            "An apple mobile which is nothing like apple",
            549,
            12.96,
            4.69,
            94,
            "Apple",
            "smartphones",
            "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
            listOf(
                "https://cdn.dummyjson.com/product-images/1/1.jpg",
                "https://cdn.dummyjson.com/product-images/1/2.jpg",
                "https://cdn.dummyjson.com/product-images/1/3.jpg",
                "https://cdn.dummyjson.com/product-images/1/4.jpg",
                "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg"
            )
        ),
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        productsAdapter.submitList(productsList)
        productsAdapter.setOnProductClickListener {
            findNavController().navigate(AllProductsFragmentDirections.actionAllProductsFragmentToProductFragment())
        }

    }

    private fun setupRecyclerView() {
        productsAdapter = AllProductsAdapter()

        binding.rvProducts.adapter = productsAdapter
        binding.rvProducts.layoutManager = GridLayoutManager(activity, 2)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
