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
//import com.cmpe133.recycledex.databinding.FragmentArticlesBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_articles.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchingFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentArticlesBinding? = null //mirrored off of profilefragment.kt
    private lateinit var database: DatabaseReference
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleArrayList: ArrayList<Article>
    private lateinit var articleSearchedList: ArrayList<Article>
    private val binding get() = _binding!! //mirrored off of profilefragment.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = layoutInflater.inflate(R.layout.fragment_search, container, false)

        articleRecyclerView = rootView.findViewById(R.id.rvsearch)
        articleRecyclerView.layoutManager = LinearLayoutManager(context)
        articleRecyclerView.setHasFixedSize(true)
        articleArrayList = arrayListOf<Article>()
        articleSearchedList = arrayListOf<Article>()
        getArticleData()
        val queryText: SearchView = rootView.findViewById(R.id.svcvFragment)
        queryText.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryText.clearFocus()

                //Jank method
                getQuery(query!!)
                for(articles in articleSearchedList)
                {
                    Toast.makeText(context, articles.title, Toast.LENGTH_SHORT).show()
                }
                setArticleList()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        //_binding = FragmentArticlesBinding.inflate(inflater, container, false) //mirrored off of profilefragment.kt
        // MUST USE onClicks in onCreateView, otherwise it will register as NULL after the onCreate!!
//        if(!articleSearchedList.isNullOrEmpty())
//        {
//            Toast.makeText(context, "NOT EMPTY", Toast.LENGTH_SHORT).show()
//        }
        return rootView
    }
    private fun getQuery(query : String){
        articleSearchedList.clear()
        for(articles: Article in articleArrayList)
        {
            val title = articles.title.lowercase()
            val description = articles.description.lowercase()
            val cate = articles.category.lowercase()
            val author = articles.author.lowercase()
            val article = arrayOf(title, description, cate, author)
            if(article.contains(query.lowercase()))
            {
               // Toast.makeText(context, articles.title, Toast.LENGTH_SHORT).show()

                   articleSearchedList.add(articles)
            }
        }
    }
    private fun setArticleList(){
        val adapter = SearchFragmentAdapter(articleSearchedList)
        articleRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : SearchFragmentAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                val link = articleSearchedList[position].link
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(link)
                startActivity(i)
                //Toast.makeText(context, articleArrayList[position].toString(), Toast.LENGTH_SHORT).show()

            }

        })
    }

    private fun getArticleData() {
        database = FirebaseDatabase.getInstance().getReference("Articles")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (ArticleSnapshot in snapshot.children) {

                        val article = ArticleSnapshot.getValue(Article::class.java)
                        articleArrayList.add(article!!)

                    }

                    val adapter = SearchFragmentAdapter(articleArrayList)
                    articleRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : SearchFragmentAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val link = articleArrayList[position].link
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(link)
                            startActivity(i)
                            //Toast.makeText(context, articleArrayList[position].toString(), Toast.LENGTH_SHORT).show()

                        }

                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

}