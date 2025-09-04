package com.dalima.wikipedia_codenicely_assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
            val (list, token) = repo.fetchCategories(prefix = "List of", accontinue = null)
            _categories.postValue(list)
            continueToken = token
            loading = false
        }
    }

    fun loadNext() {
        if (loading) return
        loading = true
        viewModelScope.launch {
            val (list, token) = repo.fetchCategories(prefix = "List of", accontinue = continueToken)
            val current = _categories.value?.toMutableList() ?: mutableListOf()
            current.addAll(list)
            _categories.postValue(current)
            continueToken = token
            loading = false
        }
    }
}

class CategoriesViewModelFactory(private val repo: WikiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoriesViewModel(repo) as T
    }
}