package com.dalima.wikipedia_codenicely_assignment.viewmodel

import androidx.lifecycle.*
import com.dalima.wikipedia_codenicely_assignment.db.CategoryEntity
import com.dalima.wikipedia_codenicely_assignment.network.WikiRepository
import kotlinx.coroutines.launch

class CategoriesViewModel(private val repo: WikiRepository) : ViewModel() {
    private val _categories = MutableLiveData<List<CategoryEntity>>()
    val categories: LiveData<List<CategoryEntity>> = _categories

    private var continueToken: String? = null
    private var loading = false

    fun loadInitial() {
        if (loading) return
        loading = true
        viewModelScope.launch {
            val (list, token) = repo.fetchCategories("List of", null)
            _categories.postValue(list)
            continueToken = token
            loading = false
        }
    }

    fun loadNext() {
        if (loading || continueToken == null) return
        loading = true
        viewModelScope.launch {
            val (list, token) = repo.fetchCategories("List of", continueToken)
            val cur = _categories.value?.toMutableList() ?: mutableListOf()
            cur.addAll(list)
            _categories.postValue(cur)
            continueToken = token
            loading = false
        }
    }
}

class CategoriesViewModelFactory(private val repo: WikiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CategoriesViewModel(repo) as T
}
