package com.mahmood_imtiaz.todaysheadlines

import android.telecom.Call
import com.mahmood_imtiaz.todaysheadlines.model.Article
import com.mahmood_imtiaz.todaysheadlines.model.News
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL ="https://newsapi.org/"
const val API_KEY ="01660707513741c5b2530cfb3c03fcd8"
interface NewsApi {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country")country:String,@Query("page")page:Int):retrofit2.Call<News>

}
object NewsServices{
    val newsInstance:NewsApi
    init {
      val retrofit=Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
        newsInstance =retrofit.create(NewsApi::class.java)
    }
}