package com.example.newsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.Constants.getTimeAgo
import com.example.newsapp.databinding.ItemBinding
import kotlinx.android.synthetic.main.item.*

class NewsAdapter(private val items: List<Articles>)
    :RecyclerView.Adapter<NewsAdapter.ViewHolder>(){

        inner class ViewHolder(val binding: ItemBinding)
            :RecyclerView.ViewHolder(binding.root){

            val ivImage = binding.ivImage
            val tvTitle = binding.tvTitle
            val tvDescription = binding.tvDescription
            val tvUrl = binding.tvUrl
            val tvPublished = binding.tvPublished
            val tvAuthor = binding.tvAuthor


            init {
                itemView.setOnClickListener {

                    val intent = Intent(itemView.context,DetailActivity::class.java)
                    intent.putExtra("news",items[adapterPosition])
                    itemView.context.startActivity(intent)

                }
            }


        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
     return items.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    val item = items[position]





            Glide
                .with(holder.itemView.context)
                .load(item.urlToImage)
                .into(holder.ivImage)
            holder.tvTitle.text = item.title
            holder.tvDescription.text = item.description
            holder.tvUrl.text = item.url
            holder.tvPublished.text = Constants.stringToDate(item.publishedAt).getTimeAgo()
            holder.tvAuthor.text = item.author


        holder.tvUrl.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW,Uri.parse(item.url))
            holder.itemView.context.startActivity(intent)


        }






    }


}