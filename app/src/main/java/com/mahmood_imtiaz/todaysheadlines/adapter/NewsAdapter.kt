package com.mahmood_imtiaz.todaysheadlines.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmood_imtiaz.todaysheadlines.activity.DetailActivity
import com.mahmood_imtiaz.todaysheadlines.R
import com.mahmood_imtiaz.todaysheadlines.model.Article

class NewsAdapter(val context: Context, val newses: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val articles = newses[position]
        holder.title.text = articles.title
        holder.discription.text = articles.description
        Glide.with(context)
            .load(articles.urlToImage)
            .into(holder.newsImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("URL", articles.url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return newses.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.newsTitle)
        var newsImage: ImageView = itemView.findViewById(R.id.newsImage)
        var discription: TextView = itemView.findViewById(R.id.newsDescription)
    }
}
