package com.example.projetblooddonation


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class MainActivity : Fragment() {
    var recyclerView: RecyclerView? = null
    var search: EditText? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var list: MutableList<User>? = null
    private var adapter: UserAdapter? = null
    private var dbref: DatabaseReference? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        list = ArrayList()
        adapter = UserAdapter(context, list as ArrayList<User>)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_donners, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout?.setOnRefreshListener(OnRefreshListener {
            swipeRefreshLayout?.setRefreshing(
                false
            )
        })
        search = view.findViewById(R.id.search)
        recyclerView = view.findViewById(R.id.recyclerView)
        dbref = FirebaseDatabase.getInstance().getReference("user")
        dbref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val user = dataSnapshot.getValue(
                        User::class.java
                    )
                    list!!.add(user!!)
                    recyclerView?.setAdapter(adapter)
                    search?.setVisibility(View.VISIBLE)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        search?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
    }

    private fun filter(text: String) {
        val filterList = ArrayList<User>()
        for (item in list!!) {
            if (item.email?.lowercase(Locale.getDefault())
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                filterList.add(item)
            }
        }
        adapter!!.FilterList(filterList)
    }
}
