package com.example.projetblooddonation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class MyRequestFragment : Fragment() {

    private var contenu: String? = null
    private var email: String? = null
    private var blood: String? = null
    private var city: String? = null
    private var tel: String? = null

    private var recyclerView: RecyclerView? = null
    private var newrqst: TextView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var btn: Button? = null

    private var list: MutableList<Request> = ArrayList()
    private var adapter: RequestAdapter? = null

    private var mAuth: FirebaseAuth? = null
    private var user: FirebaseUser? = null
    private var dbref: DatabaseReference? = null
    private var dbref2: DatabaseReference? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = RequestAdapter(context, list)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragement_my_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
        }

        newrqst = view.findViewById(R.id.new_request)
        btn = view.findViewById(R.id.add_request)
        recyclerView = view.findViewById(R.id.recyclerView)
        mAuth = FirebaseAuth.getInstance()
        user = mAuth?.currentUser
        dbref = FirebaseDatabase.getInstance().getReference("user")

        adapter?.setOnItemClickListener(object : RequestAdapter.OnItemClickListener {
            override fun onItemClick(request: Request?) {
                val position = list.indexOf(request)
                if (position != -1) {
                    list.removeAt(position)
                    adapter?.notifyItemRemoved(position)
                    dbref2 = FirebaseDatabase.getInstance().getReference("Request")
                    dbref2?.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (snap in snapshot.children) {
                                val rqst = snap.getValue(Request::class.java)
                                if (request != null) {
                                    if (rqst?.content == request.content) {
                                        snap.ref.removeValue()
                                    }
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
                }
            }
        })

        btn?.setOnClickListener {
            contenu = newrqst?.text.toString()
            email = user?.email
            if (contenu.isNullOrEmpty()) {
                newrqst?.error = "Make sure to fill the content before making the request"
            } else {
                dbref?.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (dataSnapshot in snapshot.children) {
                            val currentUser = dataSnapshot.getValue(User::class.java)
                            if (currentUser?.email == email) {
                                if (currentUser != null) {
                                    city = currentUser.city
                                }
                                if (currentUser != null) {
                                    blood = currentUser.bloodgrp
                                }
                                if (currentUser != null) {
                                    tel = currentUser.phone
                                }
                                dbref2 = FirebaseDatabase.getInstance().getReference("Request")
                                val request = Request(email, tel, blood, city, contenu)
                                dbref2?.push()?.setValue(request)
                                Toast.makeText(context, "Request added with success!", Toast.LENGTH_SHORT).show()
                                newrqst?.text = ""
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }

        try {
            dbref2 = FirebaseDatabase.getInstance().getReference("Request")
            dbref2?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
                    for (dataSnapshot in snapshot.children) {
                        val request = dataSnapshot.getValue(Request::class.java)
                        if (request?.email == currentUserEmail) {
                            if (request != null) {
                                list.add(request)
                            }
                        }
                    }
                    recyclerView?.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        } catch (ex: Exception) {
            Log.d("ERR", ex.message ?: "Unknown error")
        }
    }
}
