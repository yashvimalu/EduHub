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

public class TeacherClasses extends AppCompatActivity {

    RecyclerView rv1;
    String user_name,student_name,std,subject,link,time,board;
    DatabaseReference reference;
    TClassAdapter tClassAdapter;
    ArrayList<ClassModal> list;
    ClassModal cm1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_classes);
        rv1 = findViewById(R.id.classes_rv);
        rv1.setHasFixedSize(true);
        rv1.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        user_name = extras. getString("user_name");
        list=new ArrayList<>();

        tClassAdapter= new TClassAdapter(TeacherClasses.this,list);
        rv1.setAdapter(tClassAdapter);

        reference = FirebaseDatabase.getInstance().getReference().child("Teacher-Student");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(user_name.toLowerCase().equals(dataSnapshot.child("teacher_name").getValue().toString().toLowerCase())){
                        cm1 = dataSnapshot.getValue(ClassModal.class);
                        list.add(cm1);
                    }
                }
                tClassAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}