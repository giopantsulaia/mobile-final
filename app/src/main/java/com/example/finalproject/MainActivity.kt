package com.example.finalproject
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.dataclasses.NewsResponse
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val API_KEY = "bc534032b1msh350b3eed26cdf08p125aadjsn000e9b135ea3";
    private val HOST = "news-api14.p.rapidapi.com";
    private lateinit var news : NewsResponse;
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        articlesRecyclerView = findViewById(R.id.articles_recycler_view)
        articlesRecyclerView.layoutManager = LinearLayoutManager(this);
        progressBar = findViewById(R.id.recycler_progress_bar)
        fetchData()
    }

    private fun fetchData() {
        thread {
            val connection = createGetRequest();

            if(connection.responseCode == 200){

                val reader = connection.inputStream.reader();
                val jsonResponse = reader.readText();
                reader.close();

                news = Gson().fromJson(jsonResponse, NewsResponse::class.java);
            }

            runOnUiThread { displayArticles() }
        }
    }

    private fun displayArticles(){
        progressBar.visibility = View.GONE
        articlesRecyclerView.adapter = ArticlesAdapter(news.articles) {

            val articleFragment = ArticleFragment.newInstance(it);

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, articleFragment)
                .addToBackStack(null)
                .commit()
        };
    }

    private fun createGetRequest() : HttpURLConnection {
        val params =
                "${utf8param("country", "us")}" +
                "&${utf8param("language", "en")}" +
                "&${utf8param("pageSize", 10)}";

        val url = URL("https://news-api14.p.rapidapi.com/top-headlines?${params}");

        val connection = url.openConnection() as HttpURLConnection;

        connection.requestMethod = "GET";
        connection.setRequestProperty("X-RapidAPI-Key", API_KEY);
        connection.setRequestProperty("X-RapidAPI-Host", HOST);

        return connection;
    }
    private fun utf8param(paramName: String, paramValue: Any) : String {
        return "${encodeUtf8(paramName)}=${encodeUtf8(paramValue.toString())}";
    }
    private fun encodeUtf8(str : String) : String {
        return URLEncoder.encode(str, "UTF-8");
    }
}