package ru.aapodomatko.newsapp.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.aapodomatko.newsapp.databinding.FragmentFavoriteBinding
import ru.aapodomatko.newsapp.ui.adapters.NewsAdapter

@AndroidEntryPoint
class FavoriteFragment() : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val mBinding get() = _binding!!

    lateinit var newsAdapter: NewsAdapter

    private val mViewModel by viewModels<FavoriteViewModel>()




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        mViewModel.favoriteNewsLiveData.observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)

            articles.forEach { article ->
                if (article.isLiked == false) {
                    mViewModel.deleteFavoriteArticle(article)
                } else {
                    Log.d("FavoriteFragment", "Error")
                }
            }

            val count = articles.size
            mBinding.favoriteNewsCount.text = count.toString()

        }

    }


    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        mBinding.favoriteNewsAdapter.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}



