package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar()


        val news = intent.getSerializableExtra("news") as Articles

        tvDetailTitle.text = news.title
        Glide
            .with(this)
            .load(news.urlToImage)
            .into(ivDetailImage)
        tvDetailContent.text = news.content
        tvDetailAuthor.text = news.author

    }


    private fun toolbar(){

        setSupportActionBar(toolbar_detail)

        toolbar_detail.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolbar_detail.setNavigationOnClickListener {

            onBackPressed()

        }


    }
}