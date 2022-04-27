package com.cmpe133.recycledex

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpe133.recycledex.databinding.FragmentArticlesBinding
import com.cmpe133.recycledex.databinding.FragmentProfileBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.search_veiw_fragment.*
import kotlinx.android.synthetic.main.search_veiw_fragment.view.*
import kotlinx.coroutines.selects.SelectInstance
import androidx.databinding.DataBindingUtil.setContentView as setContentView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment(R.layout.search_veiw_fragment) {
    private var _binding: FragmentArticlesBinding? = null
    private lateinit var database: DatabaseReference
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleArrayList: ArrayList<Article>
    private lateinit var article: Article
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().getReference("Articles")
        getArticleData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = layoutInflater.inflate(R.layout.search_veiw_fragment, container, false)
        // reference to the recycler view id
        articleRecyclerView = rootView.findViewById(R.id.rvsearch)
        articleRecyclerView.layoutManager = LinearLayoutManager(context)
        articleRecyclerView.setHasFixedSize(true)
        articleArrayList = arrayListOf<Article>()
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        //MUST USE onClicks in onCreateView, otherwise it will register as NULL after the onCreate!!
        //when you click on card, should redirects to website (right now to homepage)
        binding.cvItem.setOnClickListener{
            val intent = Intent(context, HomePageActivity::class.java)
            startActivity(intent)
        }
        return binding.root
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
}

