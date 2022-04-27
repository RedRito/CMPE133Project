package com.cmpe133.recycledex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentArticlesBinding? = null //mirrored off of profilefragment.kt
    private lateinit var database: DatabaseReference
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleArrayList: ArrayList<Article>
    //private lateinit var article: Article
    //private val binding get() = _binding!! //mirrored off of profilefragment.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArticleData()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = layoutInflater.inflate(R.layout.fragment_search, container, false)
        // reference to the recycler view id
        articleRecyclerView = rootView.findViewById(R.id.rvsearch)
        articleRecyclerView.layoutManager = LinearLayoutManager(context)
        articleRecyclerView.setHasFixedSize(true)
        articleArrayList = arrayListOf<Article>()
        //_binding = FragmentArticlesBinding.inflate(inflater, container, false) //mirrored off of profilefragment.kt
        // MUST USE onClicks in onCreateView, otherwise it will register as NULL after the onCreate!!
        // when you click on card, should redirects to website (right now to homepage)
        /**
        rootView.cvItem.setOnClickListener{
            val intent = Intent(context, HomePageActivity::class.java)
            startActivity(intent)
        }
        **/
        return rootView
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
                    articleRecyclerView.adapter = SearchFragmentAdapter(articleArrayList)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        /**
        database = FirebaseDatabase.getInstance().getReference("Articles")
        database.child(id).get().addOnSuccessListener {
        if(it.exists())
        {
        val title = it.child("userName").value
        val description = it.child("description").value
        val category = it.child("category").value
        val author = it.child("author").value
        binding.tvArticleTitle.text = title.toString()
        binding.tvArticleDescription.text = description.toString()
        binding.tvArticleAuthor.text = author.toString()
        }
        else
        {
        Toast.makeText(context, "Article does not exist", Toast.LENGTH_SHORT).show()
        }
        }.addOnFailureListener{
        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
         **/
    }
    /**
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    **/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}