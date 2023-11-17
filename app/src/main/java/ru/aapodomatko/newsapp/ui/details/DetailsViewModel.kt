package ru.aapodomatko.newsapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.aapodomatko.newsapp.data.api.NewsRepository
import ru.aapodomatko.newsapp.models.Article
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    init {
        getSavedArticles()
    }

    private fun getSavedArticles() = viewModelScope.launch(Dispatchers.IO) {
        val res = repository.getFavoriteArticles()
        println("DB size: ${res.size}")
        repository.getFavoriteArticles()
    }

    fun saveFavoriteArticles(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.addToFavorite(article = article)
    }
}