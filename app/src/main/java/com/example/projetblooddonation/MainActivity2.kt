package com.example.projetblooddonation

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var email: String? = null
    var city: String? = null
    var grp: String? = null
    var mail: TextView? = null
    var City: TextView? = null
    var Grp: TextView? = null
    private var drawer: DrawerLayout? = null
    private var mAuth: FirebaseAuth? = null
    private var user: FirebaseUser? = null
    private var dbref: DatabaseReference? = null
    var rootView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer?.addDrawerListener(toggle)
        mAuth = FirebaseAuth.getInstance()
        user = mAuth!!.currentUser
        dbref = FirebaseDatabase.getInstance().getReference("user")
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        toggle.syncState()
        if (user != null) {
            val uid = user!!.uid
            val currentmail = user!!.email
            dbref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val User = snapshot.getValue(
                            User::class.java
                        )
                        if ((User!!.email) == currentmail) {
                            rootView = findViewById(R.id.nav_view)
                            mail = rootView?.findViewById(R.id.mail)
                            City = rootView?.findViewById(R.id.city)
                            Grp = rootView?.findViewById(R.id.blood)
                            mail?.setText(snapshot.child("email").getValue(String::class.java))
                            City?.setText(snapshot.child("city").getValue(String::class.java))
                            Grp?.setText(snapshot.child("bloodgrp").getValue(String::class.java))
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                MainActivity()
            ).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_home) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                MainActivity()
            ).commit()
        } else if (item.itemId == R.id.nav_my_requests) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                MyRequestFragment()
            ).commit()
        } else if (item.itemId == R.id.nav_requests) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                RequestsFragment()
            ).commit()
        } else if (item.itemId == R.id.nav_Disconnect) {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity2, LoginPage::class.java))
        } else if (item.itemId == R.id.nav_near_me) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                MapFragment()
            ).commit()
        } else if (item.itemId == R.id.nav_profile) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ProfileFragment()
            ).commit()
        }
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }
}