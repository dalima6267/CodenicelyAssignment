package com.dalima.wikipedia_codenicely_assignment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dalima.wikipedia_codenicely_assignment.viewmodel.CategoriesViewModel
import com.dalima.wikipedia_codenicely_assignment.viewmodel.CategoriesViewModelFactory
import com.dalima.wikipedia_codenicely_assignment.adapter.CategoryAdapter
import com.dalima.wikipedia_codenicely_assignment.databinding.FragmentListBinding
import com.dalima.wikipedia_codenicely_assignment.db.AppDatabase
import com.dalima.wikipedia_codenicely_assignment.network.WikiRepository

class CategoriesFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CategoriesViewModel
    private val adapter = CategoryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dao = AppDatabase.Companion.getInstance(requireContext()).wikiDao()
        val repo = WikiRepository(dao)
        viewModel = ViewModelProvider(this, CategoriesViewModelFactory(repo)).get(
            CategoriesViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadInitial()
        }

        viewModel.categories.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.swipeRefresh.isRefreshing = false
        }

        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                val lm = rv.layoutManager as LinearLayoutManager
                if (lm.findLastVisibleItemPosition() >= lm.itemCount - 3) {
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