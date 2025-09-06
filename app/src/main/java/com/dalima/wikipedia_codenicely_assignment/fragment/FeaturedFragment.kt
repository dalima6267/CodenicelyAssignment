package com.dalima.wikipedia_codenicely_assignment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dalima.wikipedia_codenicely_assignment.viewmodel.FeaturedViewModel
import com.dalima.wikipedia_codenicely_assignment.viewmodel.FeaturedViewModelFactory
import com.dalima.wikipedia_codenicely_assignment.adapter.ImageAdapter
import com.dalima.wikipedia_codenicely_assignment.databinding.FragmentListBinding
import com.dalima.wikipedia_codenicely_assignment.db.AppDatabase
import com.dalima.wikipedia_codenicely_assignment.network.WikiRepository

class FeaturedFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FeaturedViewModel
    private val adapter = ImageAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dao = AppDatabase.Companion.getInstance(requireContext()).wikiDao()
        val repo = WikiRepository(dao)
        viewModel = ViewModelProvider(this, FeaturedViewModelFactory(repo)).get(FeaturedViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadInitial()
        }

        viewModel.images.observe(viewLifecycleOwner) { list ->
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