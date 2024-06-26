package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.nurdaulet.dummyproducts.R
import ru.nurdaulet.dummyproducts.application.DummyApplication
import ru.nurdaulet.dummyproducts.databinding.FragmentAllProductsBinding
import ru.nurdaulet.dummyproducts.di.ViewModelFactory
import ru.nurdaulet.dummyproducts.domain.models.Product
import ru.nurdaulet.dummyproducts.utils.showToast
import ru.nurdaulet.dummyproducts.utils.viewGone
import ru.nurdaulet.dummyproducts.utils.viewVisible
import javax.inject.Inject

class AllProductsFragment : Fragment(R.layout.fragment_all_products) {

    private val binding: FragmentAllProductsBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as DummyApplication).component
    }

    private lateinit var viewModel: AllProductsVM
    private lateinit var pagingAdapter: ProductsPagingAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupRecyclerView()
        viewModelObservers()
        setAdapterListeners()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[AllProductsVM::class.java]
    }

    private fun setupRecyclerView() {
        pagingAdapter = ProductsPagingAdapter()

        binding.rvProducts.apply {
            adapter = pagingAdapter
            layoutManager = GridLayoutManager(activity, 2)

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
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getProductsPaging().collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collect {
                if (it.refresh is LoadState.Loading) {
                    binding.loadingBar.viewVisible()
                }
                if (it.refresh is LoadState.NotLoading && pagingAdapter.itemCount == 0) {
                    binding.loadingBar.viewGone()
                }
                if (it.refresh is LoadState.NotLoading && pagingAdapter.itemCount != 0) {
                    binding.loadingBar.viewGone()
                }
                if (it.refresh is LoadState.Error) {
                    showToast((it.refresh as LoadState.Error).error.message.toString())
                }
            }
        }
    }

    private fun setAdapterListeners() {
        pagingAdapter.setOnProductClickListener { product -> navigateToProductFragment(product) }
    }

    private fun navigateToProductFragment(product: Product) {
        findNavController().navigate(
            AllProductsFragmentDirections.actionAllProductsFragmentToProductFragment(
                product
            )
        )
    }

    override fun onDestroyView() {
        binding.rvProducts.adapter = null
        super.onDestroyView()
    }
}
