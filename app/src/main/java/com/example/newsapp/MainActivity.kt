package com.example.newsapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {


    private lateinit var newsAdapter: NewsAdapter





    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getNewsListener()
        getNews(category = "general", query = "" ) // default

        btnSearch.setOnClickListener {
            val query = etSearch.text.toString()
            etSearch.text.clear()
            getNews(category = "general", query = query)
        }


    }

    private fun getNewsListener() {


        btnAll.setOnClickListener {

            getNews(category = "general",)
        }

        btnBusiness.setOnClickListener {

            getNews(category = "business",)
        }

        btnEntertainment.setOnClickListener {

            getNews(category = "entertainment")
        }

        btnHealt.setOnClickListener {

            getNews(category = "health")
        }

        btnScience.setOnClickListener {

            getNews(category = "science")
        }

        btnSports.setOnClickListener {
            getNews(category = "sports")
        }

        btnTechnology.setOnClickListener {
            getNews(category = "technology")
        }
    }

    private fun setUpRecyclerView(newsList:List<Articles>){


         newsAdapter = NewsAdapter(newsList)
        rv_news.adapter = newsAdapter
        rv_news.layoutManager = LinearLayoutManager(this)




    }






    private fun getNews(category:String,query:String =""){

        if(isNetworkAvailable(this)){


            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service: NewsInterface =
                retrofit.create<NewsInterface>(NewsInterface::class.java)

            val call : Call<NewsResponse> = service.getNews(
                Constants.COUNTRY_CODE,
                category,
                Constants.PAGE_SIZE,
                Constants.APP_KEY,


            )

            showDialog()

            call.enqueue(object:Callback<NewsResponse> {
                @SuppressLint("SuspiciousIndentation")
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if(response!!.isSuccessful){

                     hideDialog()
                     val newsList:NewsResponse = response.body()!!

                        val filteredList =

                            if(query.isNotEmpty()){

                               newsList.articles.filter {

                                       article->

                                   article.title.contains(query,true)
                               }

                           }
                           else
                           {

                               newsList.articles

                           }

                        setUpRecyclerView(filteredList)





                    }
                    else{

                        when(response.code()){

                            400 -> {

                                Log.e("Error 400", "Bad Connection")

                            }

                            404 -> {

                                Log.e("Error 404", "Not Found")
                            }

                            else -> {

                                Log.e("Error"," Generic Error")
                            }


                        }

                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.e("error",t!!.message.toString())
                    hideDialog()
                }


            })


        }else{


            Toast.makeText(
                this@MainActivity,"Yes Internet"
                , Toast.LENGTH_SHORT
            ).show()

        }



    }


    private fun showDialog(){

        dialog = Dialog(this)

        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog)

        dialog.show()

    }

    private fun hideDialog(){

        if(dialog!=null){

            dialog.dismiss()
        }

    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetWork =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
    }



}