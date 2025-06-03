package com.example.projetblooddonation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {
    var Email: TextInputEditText? = null
    var password: TextInputEditText? = null
    var city: TextInputEditText? = null
    var bloodgrp: TextInputEditText? = null
    var phone: TextInputEditText? = null
    var City: String? = null
    var Bloodgrp: String? = null
    var Phone: String? = null
    private var mAuth: FirebaseAuth? = null
    private var user: FirebaseUser? = null
    private var modify: Button? = null
    private var dbref: DatabaseReference? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Email = view.findViewById(R.id.Email)
        city = view.findViewById(R.id.city)
        password = view.findViewById(R.id.password)
        bloodgrp = view.findViewById(R.id.bld)
        phone = view.findViewById(R.id.number)
        mAuth = FirebaseAuth.getInstance()
        user = mAuth!!.currentUser
        modify = view.findViewById(R.id.modify)
        modify?.setOnClickListener(View.OnClickListener {
            City = city?.getText().toString()
            Bloodgrp = bloodgrp?.getText().toString()
            Phone = phone?.getText().toString()

            // Create a separate reference for updating data
            val updateRef = FirebaseDatabase.getInstance().getReference("user")
            if (user != null) {
                val currentmail = user!!.email
                updateRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            val user = snapshot.getValue(
                                User::class.java
                            )
                            if (user != null && user.email == currentmail) {
                                val uid = snapshot.key

                                // Use the uid to create a child reference for the specific user
                                val userRef = updateRef.child(uid!!)

                                // Update the specific fields
                                val updates: MutableMap<String, Any> = HashMap()
                                updates["phone"] = Phone!!
                                updates["city"] = City!!
                                updates["bloodgrp"] = Bloodgrp!!

                                userRef.updateChildren(updates)
                                    .addOnSuccessListener { aVoid: Void? ->
                                        Toast.makeText(
                                            context,
                                            "Profile updated successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .addOnFailureListener { e: Exception? ->
                                        Toast.makeText(
                                            context, "Failed to update profile", Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle the error
                    }
                })
            }
        })

        dbref = FirebaseDatabase.getInstance().getReference("user")
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
                            Email?.setText(snapshot.child("email").getValue(String::class.java))
                            city?.setText(snapshot.child("city").getValue(String::class.java))
                            bloodgrp?.setText(
                                snapshot.child("bloodgrp").getValue(
                                    String::class.java
                                )
                            )
                            phone?.setText(snapshot.child("phone").getValue(String::class.java))
                            password?.setText(
                                snapshot.child("password").getValue(
                                    String::class.java
                                )
                            )
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }
}
