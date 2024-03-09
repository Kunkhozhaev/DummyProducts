package ru.nurdaulet.dummyproducts.presentation.screens.all_products

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ru.nurdaulet.dummyproducts.application.DummyApplication
import ru.nurdaulet.dummyproducts.databinding.FragmentAllProductsBinding
import ru.nurdaulet.dummyproducts.presentation.ViewModelFactory
import ru.nurdaulet.dummyproducts.utils.Resource
import javax.inject.Inject

class AllProductsFragment : Fragment() {

    private var _binding: FragmentAllProductsBinding? = null
    private val binding: FragmentAllProductsBinding
        get() = _binding ?: throw RuntimeException("FragmentAllProductsBinding == null")

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
        viewModel.getProducts(0)
        viewModelObservers()

        productsAdapter.setOnProductClickListener { product ->
            findNavController().navigate(AllProductsFragmentDirections.actionAllProductsFragmentToProductFragment(product))
        }

    }

    private fun setupRecyclerView() {
        productsAdapter = AllProductsAdapter()

        binding.rvProducts.adapter = productsAdapter
        binding.rvProducts.layoutManager = GridLayoutManager(activity, 2)
    }

    private fun viewModelObservers() {
        viewModel.products.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { products ->
                        productsAdapter.submitList(products)
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(requireActivity(), response.message, Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Loading -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
