package ru.aapodomatko.newsapp.data.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.aapodomatko.newsapp.data.db.ArticleDao
import ru.aapodomatko.newsapp.models.Article
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val newsService: NewsService,
    private val articleDao: ArticleDao
) {
    suspend fun getNews(countryCode: String, pageNumber: Int) =
        newsService.getHeadLines(countryCode = countryCode, page = pageNumber)

    suspend fun searchNews(query: String, pageNumber: Int) =
        newsService.getEverything(query = query, page = pageNumber)

    suspend fun getFavoriteArticles() = withContext(Dispatchers.IO) {
        articleDao.getAllArticles()
    }

    suspend fun addToFavorite(article: Article) = articleDao.insert(article = article)

    suspend fun deleteFromFavorite(article: Article) = articleDao.delete(article = article)
}