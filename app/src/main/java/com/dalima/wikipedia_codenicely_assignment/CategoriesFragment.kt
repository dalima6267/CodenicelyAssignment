package com.dalima.wikipedia_codenicely_assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dalima.wikipedia_codenicely_assignment.databinding.FragmentListBinding


class CategoriesFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CategoriesViewModel
    private val adapter = CategoryAdapter() // You’ll need a CategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dao = AppDatabase.getInstance(requireContext()).wikiDao()
        val repo = WikiRepository(dao)
        viewModel = ViewModelProvider(this, CategoriesViewModelFactory(repo)).get(CategoriesViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadInitial()
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.categories.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        binding.recyclerView.addOnScrollListener(object: androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                val lm = rv.layoutManager as LinearLayoutManager
                val total = lm.itemCount
                val last = lm.findLastVisibleItemPosition()
                if (total > 0 && last >= total - 3) {
                    viewModel.loadNext()
                }
            }
        })

        viewModel.loadInitial()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}