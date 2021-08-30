package com.mahmood_imtiaz.todaysheadlines.activity

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.littlemango.stacklayoutmanager.StackLayoutManager
import com.mahmood_imtiaz.todaysheadlines.ColorPicker
import com.mahmood_imtiaz.todaysheadlines.NewsServices
import com.mahmood_imtiaz.todaysheadlines.R
import com.mahmood_imtiaz.todaysheadlines.adapter.NewsAdapter
import com.mahmood_imtiaz.todaysheadlines.model.Article
import com.mahmood_imtiaz.todaysheadlines.model.News
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()
    var pagenum: Int = 1
    var totalResult: Int = -1
    val TAG: String = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNews()
        adapter = NewsAdapter(this@MainActivity, articles)
        newsList.adapter = adapter
        // newsList.layoutManager=LinearLayoutManager(this@MainActivity)
        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(1000)
        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {
            override fun onItemChanged(position: Int) {
                mainContianer.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                Log.d(TAG, "First Visible Item- ${layoutManager.getFirstVisibleItemPosition()}")
                Log.d(TAG, "Total Count- ${layoutManager.itemCount}")
                if (totalResult > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount - 5) {
                    pagenum++
                    getNews()
                }
            }

        })
        newsList.layoutManager = layoutManager
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private fun getNews() {
        Log.d(TAG, "Request Sent For $pagenum")
        val news = NewsServices.newsInstance.getHeadlines("us", pagenum)
        news.enqueue(object : Callback<News> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news: News? = response.body()
                if (news != null) {
                    totalResult = news.totalResults
                    Log.d("Mahmood", news.toString())
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Mahmood", "Error", t)
            }

        })
    }
}