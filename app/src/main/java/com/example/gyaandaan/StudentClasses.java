package com.example.gyaandaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentClasses extends AppCompatActivity {
    RecyclerView rv1;
    String user_name,teacher_name,std,subject,link,time,board;
    DatabaseReference reference;
    SClassAdapter sClassAdapter;
    ArrayList<ClassModal> list;
    ClassModal cm1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_classes);
        rv1 = findViewById(R.id.classes_rv);
        rv1.setHasFixedSize(true);
        rv1.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        user_name = extras.getString("student_name");
        list=new ArrayList<>();
        sClassAdapter= new SClassAdapter(this,list);
        rv1.setAdapter(sClassAdapter);


        reference = FirebaseDatabase.getInstance().getReference().child("Teacher-Student");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(user_name.toLowerCase().equals(dataSnapshot.child("student_name").getValue().toString().toLowerCase())){
                        cm1 = dataSnapshot.getValue(ClassModal.class);
                        list.add(cm1);
                    }
                }sClassAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    }
