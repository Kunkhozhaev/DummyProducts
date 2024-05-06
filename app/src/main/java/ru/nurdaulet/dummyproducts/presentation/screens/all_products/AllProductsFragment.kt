package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.nurdaulet.dummyproducts.R
import ru.nurdaulet.dummyproducts.application.DummyApplication
import ru.nurdaulet.dummyproducts.databinding.FragmentAllProductsBinding
import ru.nurdaulet.dummyproducts.di.ViewModelFactory
import ru.nurdaulet.dummyproducts.domain.models.Product
import ru.nurdaulet.dummyproducts.presentation.screens.all_products.paging_adapter.ProductsPagingAdapter
import ru.nurdaulet.dummyproducts.utils.Constants.LIMIT
import javax.inject.Inject

class AllProductsFragment : Fragment() {

    private var _binding: FragmentAllProductsBinding? = null
    private val binding: FragmentAllProductsBinding
        get() = _binding ?: throw RuntimeException("FragmentAllProductsBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as DummyApplication).component
    }

    private lateinit var viewModel: AllProductsVM
    private lateinit var productsAdapter: AllProductsAdapter
    private lateinit var pagingAdapter: ProductsPagingAdapter

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
        initViewModel()
        viewModel.getProducts()

        setupRecyclerView()
        viewModelObservers()
        setAdapterListeners()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[AllProductsVM::class.java]
    }

    private fun setupRecyclerView() {
        productsAdapter = AllProductsAdapter()
        pagingAdapter = ProductsPagingAdapter()

        binding.rvProducts.apply {
            adapter = pagingAdapter
            layoutManager = GridLayoutManager(activity, 2)
            addOnScrollListener(productsScrollListener)

            val offset = resources.getDimension(R.dimen.rv_item_offset).toInt()
            addItemDecoration(
                ProductsOffsetItemDecoration(
                    bottomOffset = offset,
                    leftOffset = offset,
                    rightOffset = offset,
                    topOffset = offset
                )
            )
        }
    }

    private fun viewModelObservers() {
//        viewModel.products.observe(viewLifecycleOwner) { response ->
//            when (response) {
//                is Resource.Success -> {
//                    binding.loadingBar.viewGone()
//                    isLoading = false
//                    response.data?.let { products ->
//                        if (products.isNotEmpty()) {
//                            productsAdapter.submitList(products.toList())
//                            viewModel.productsOffset += LIMIT
//                            isLastProduct = false
//                        } else {
//                            isLastProduct = true
//                        }
//                    }
//                }
//
//                is Resource.Error -> {
//                    isLoading = false
//                    binding.loadingBar.viewGone()
//                    toast(response.message.toString())
//                }
//
//                is Resource.Loading -> {
//                    isLoading = true
//                    if (viewModel.productsOffset == 100) {
//                        binding.loadingBar.viewGone()
//                    } else {
//                        binding.loadingBar.viewVisible()
//                    }
//                }
//            }
//        }
        viewModel.getProductsPaging().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                //TODO do we rlly need State.RESUMED?
                pagingAdapter.submitData(it)
            }
        }
    }

    private fun setAdapterListeners() {
        productsAdapter.setOnProductClickListener { product ->
            viewModel.productsOffset = 0
            viewModel.productsResponse = null
            navigateToProductFragment(product)
        }
        pagingAdapter.setOnProductClickListener { product ->
            navigateToProductFragment(product)
        }

    }

    private fun navigateToProductFragment(product: Product) {
        findNavController().navigate(
            AllProductsFragmentDirections.actionAllProductsFragmentToProductFragment(
                product
            )
        )
    }

    /**
     * Setting logic for pagination
     */
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
        binding.rvProducts.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
