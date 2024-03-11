package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nurdaulet.dummyproducts.application.DummyApplication
import ru.nurdaulet.dummyproducts.databinding.FragmentAllProductsBinding
import ru.nurdaulet.dummyproducts.presentation.ViewModelFactory
import ru.nurdaulet.dummyproducts.utils.Constants.LIMIT
import ru.nurdaulet.dummyproducts.utils.Resource
import javax.inject.Inject

class AllProductsFragment : Fragment() {

    private var _binding: FragmentAllProductsBinding? = null
    private val binding: FragmentAllProductsBinding
        get() = _binding ?: throw RuntimeException("FragmentAllProductsBinding == null")


    @Inject
    lateinit var application: Application

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AllProductsVM

    private val component by lazy {
        (requireActivity().application as DummyApplication).component
    }

    private lateinit var productsAdapter: AllProductsAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel = ViewModelProvider(this, viewModelFactory)[AllProductsVM::class.java]
        //TODO For the first start get firstly from db. If not exist from network converted to db again
        viewModel.getProducts()
        viewModelObservers()

        productsAdapter.setOnProductClickListener { product ->
            findNavController().navigate(
                AllProductsFragmentDirections.actionAllProductsFragmentToProductFragment(
                    product
                )
            )
        }

    }

    private fun setupRecyclerView() {
        productsAdapter = AllProductsAdapter(application)

        binding.rvProducts.apply {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(activity, 2)
            addOnScrollListener(productsScrollListener)
        }
    }

    private fun viewModelObservers() {
        viewModel.products.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.loadingBar.visibility = View.GONE
                    response.data?.let { products ->
                        Log.d("Pagination", "---ON SUCCESS---")
                        Log.d("Pagination", "Products size: ${products.size}")
                        //TODO Если долистать до 100 прдоуктов -> зайти в продукт -> вернуться, то ничего не отобразится
                        if (products.isNotEmpty()) {
                            productsAdapter.submitList(products.toList())
                            viewModel.productsOffset += LIMIT
                            isLastProduct = false
                        } else {
                            isLastProduct = true
                        }
                    }
                }

                is Resource.Error -> {
                    binding.loadingBar.visibility = View.GONE
                    Toast.makeText(requireActivity(), response.message, Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Loading -> {
                    if (viewModel.productsOffset == 100) {
                        binding.loadingBar.visibility = View.GONE
                    } else {
                        binding.loadingBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private var isLoading = false
    private var isLastProduct = false
    private var isScrolling = false

    private val productsScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastProduct = !isLoading && !isLastProduct
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= LIMIT
            val shouldPaginate = isNotLoadingAndNotLastProduct && isAtLastItem &&
                    isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getProducts()
                isScrolling = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
