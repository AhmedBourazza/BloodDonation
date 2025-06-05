package com.example.projetblooddonation;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestsFragment extends Fragment {
    RecyclerView recyclerView;
    EditText search;
    TextView userName;

    SwipeRefreshLayout swipeRefreshLayout;
    private List<Request> list;
    private RequestAdapter2 adapter;
    private DatabaseReference dbref;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        list= new ArrayList<>();
        adapter = new RequestAdapter2(context,list);
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_requests,container,false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageViewCurrent = view.findViewById(R.id.imageViewCurrent);

        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        search = view.findViewById(R.id.search);
        recyclerView= view.findViewById(R.id.recyclerView);
        dbref= FirebaseDatabase.getInstance().getReference("Request");
        userName = view.findViewById(R.id.userName);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String currentEmail = currentUser.getEmail();
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user");

            usersRef.orderByChild("email").equalTo(currentEmail)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String name = snapshot.child("name").getValue(String.class);
                                String imageBase64 = snapshot.child("imageBase64").getValue(String.class);
                                if (imageBase64 != null && !imageBase64.isEmpty()) {
                                    try {
                                        byte[] decodedString = android.util.Base64.decode(imageBase64, android.util.Base64.DEFAULT);
                                        android.graphics.Bitmap decodedByte = android.graphics.BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                        if (decodedByte != null) {
                                            imageViewCurrent.setImageBitmap(decodedByte);
                                        }
                                    } catch (IllegalArgumentException e) {
                                        e.printStackTrace();
                                        // Gérer erreur de décodage
                                    }
                                } else {
                                    // Optionnel : image par défaut si aucune image Base64
                                    imageViewCurrent.setImageResource(R.drawable.default_user_image);
                                }

                                if (name != null) {
                                   // userName.setText(name);
                                } else {
                                  //  userName.setText("Nom non trouvé");
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            userName.setText("Erreur de chargement");
                        }
                    });
        }

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Request request= dataSnapshot.getValue(Request.class);
                    list.add(request);
                    recyclerView.setAdapter(adapter);
                    search.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<Request> filterList= new ArrayList<>();
        for(Request item:list){
            if(item.email.toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        adapter.FilterList(filterList);
    }
}
