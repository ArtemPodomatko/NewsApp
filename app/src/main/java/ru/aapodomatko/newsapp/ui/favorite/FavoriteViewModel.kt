package ru.aapodomatko.newsapp.ui.favorite

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.aapodomatko.newsapp.data.api.NewsRepository
import ru.aapodomatko.newsapp.models.Article
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val favoriteNewsLiveData: MutableLiveData<List<Article>> = MutableLiveData()


    init {
        getFavoriteArticles()
    }

    fun getFavoriteArticles() {
        viewModelScope.launch {
            val articles = repository.getFavoriteArticles()
            if (articles.isNotEmpty()) {
                favoriteNewsLiveData.value = articles
            } else {
                Log.d("MyLog", "Error!")
            }
        }
    }

    fun deleteFavoriteArticle(article: Article) {
        viewModelScope.launch {
            repository.deleteFromFavorite(article)
            getFavoriteArticles()

        }
    }
}