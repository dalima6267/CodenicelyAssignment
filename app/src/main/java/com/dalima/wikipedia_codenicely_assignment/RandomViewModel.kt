package com.dalima.wikipedia_codenicely_assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class RandomViewModel(private val repo: WikiRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<ArticleEntity>>()
    val articles: LiveData<List<ArticleEntity>> = _articles

    private var continueToken: String? = null
    private var loading = false

    fun loadInitial() {
        if (loading) return
        loading = true
        viewModelScope.launch {
            val (list, token) = repo.fetchRandomArticles(continueToken = null)
            _articles.postValue(list)
            continueToken = token
            loading = false
        }
    }

    fun loadNext() {
        if (loading) return
        loading = true
        viewModelScope.launch {
            val (list, token) = repo.fetchRandomArticles(continueToken = continueToken)
            val current = _articles.value?.toMutableList() ?: mutableListOf()
            current.addAll(list)
            _articles.postValue(current)
            continueToken = token
            loading = false
        }
    }
}

class RandomViewModelFactory(private val repo: WikiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RandomViewModel(repo) as T
    }
}