package com.cmpe133.recycledex

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpe133.recycledex.databinding.FragmentArticlesBinding
import com.google.firebase.auth.FirebaseAuth
//import com.cmpe133.recycledex.databinding.FragmentArticlesBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_articles.view.*

class SearchingFragment : Fragment(R.layout.fragment_search) {
    private lateinit var database: DatabaseReference
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleArrayList: ArrayList<Article>
    private lateinit var articleSearchedList: ArrayList<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //inflate the page
        val rootView: View = layoutInflater.inflate(R.layout.fragment_search, container, false)
        //intialize variables
        articleRecyclerView = rootView.findViewById(R.id.rvsearch)
        articleRecyclerView.layoutManager = LinearLayoutManager(context)
        articleRecyclerView.setHasFixedSize(true)
        articleArrayList = arrayListOf<Article>()
        articleSearchedList = arrayListOf<Article>()
        var topText: TextView = rootView.findViewById(R.id.tvSuggestedArticles)


        //Get the article data to set to recyclerview
        getArticleData()
        //when a query is entered in the keyboard, gets the search results from the list, sets to recyclerview
        val queryText: SearchView = rootView.findViewById(R.id.svcvFragment)
        queryText.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryText.clearFocus()

                getQuery(query!!)
                topText.text = "Results"
                setArticleList()



                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        return rootView
    }

    //gets the query from the list
    //sets into a intiaized list
    private fun getQuery(query : String){
        articleSearchedList.clear()
        for(articles: Article in articleArrayList)
        {
            val title = articles.title.lowercase()
            val description = articles.description.lowercase()
            val cate = articles.category.lowercase()
            val author = articles.author.lowercase()
            val query = query.lowercase()
            if(title.contains(query) || description.contains(query) || cate.contains(query) || author.contains(query))
            {
                   articleSearchedList.add(articles)
            }
        }
    }
    //sets the list of items into the recyclerview
    private fun setArticleList(){
        val adapter = SearchFragmentAdapter(articleSearchedList)
        articleRecyclerView.adapter = adapter
        //when clicked on an item, brings them to the given link
        adapter.setOnItemClickListener(object : SearchFragmentAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                val link = articleSearchedList[position].link
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(link)
                startActivity(i)

            }

        })
    }
    //gets the article data from the database
    //sets the intialized list of articles
    private fun getArticleData() {
        database = FirebaseDatabase.getInstance().getReference("Articles")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ArticleSnapshot in snapshot.children) {

                        val article = ArticleSnapshot.getValue(Article::class.java)
                        articleArrayList.add(article!!)

                    }
                    //sets the list of items into the recyclerview
                    val adapter = SearchFragmentAdapter(articleArrayList)
                    articleRecyclerView.adapter = adapter
                    //when clicked on an item, brings them to the given link
                    adapter.setOnItemClickListener(object : SearchFragmentAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val link = articleArrayList[position].link
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(link)
                            startActivity(i)


                        }

                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

}