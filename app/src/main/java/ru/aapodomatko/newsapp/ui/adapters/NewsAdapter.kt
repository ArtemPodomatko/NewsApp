package ru.aapodomatko.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.aapodomatko.newsapp.R
import ru.aapodomatko.newsapp.models.Article


class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            val articleImage: ImageView = findViewById(R.id.article_image)
            val articleTitle: TextView = findViewById(R.id.article_title)
            val articleData: TextView = findViewById(R.id.article_data)
            val heartImage: ImageView = findViewById(R.id.heart_icon)
            Glide.with(this).load(article.urlToImage).into(articleImage)
            articleImage.clipToOutline = true
            articleTitle.text = article.title
            articleData.text = article.publishedAt
            if (article.isLiked == true) {
                heartImage.setColorFilter(ContextCompat.getColor(context, R.color.red))
            } else {
                heartImage.setColorFilter(ContextCompat.getColor(context, R.color.black))
            }

            heartImage.setOnClickListener {
                article.isLiked = false
                removeItem(article)
            }

            setOnClickListener {
                onItemCLickListener?.let { it(article) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemCLickListener: ((Article) -> Unit)? = null

    fun setOnClickListener(listener: (Article) -> Unit) {
        onItemCLickListener = listener
    }

    private fun removeItem(article: Article) {
        val currentList = ArrayList(differ.currentList)
        currentList.remove(article)
        differ.submitList(currentList)
    }




}