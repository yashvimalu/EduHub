package com.example.gyaandaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherSchedule extends AppCompatActivity {

    RecyclerView rv1;
    String user_name,student_name,std,subject,board;
    DatabaseReference reference;
    TClassScehdule tClassScehdule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_schedule);

        rv1 = findViewById(R.id.schedule_rv);
        rv1.setHasFixedSize(true);
        rv1.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        user_name = extras.getString("user_name");

        reference = FirebaseDatabase.getInstance().getReference().child("Teacher-Student");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(user_name.toLowerCase().equals(dataSnapshot.child("teacher_name").getValue().toString().toLowerCase())){
                        student_name= dataSnapshot.child("student_name").getValue().toString();
                        std= dataSnapshot.child("standard").getValue().toString();
                        subject= dataSnapshot.child("subject").getValue().toString();
                        board = dataSnapshot.child("board").getValue().toString();
                    }
                }

                tClassScehdule= new TClassScehdule(TeacherSchedule.this,student_name,user_name,std,subject,board);
                rv1.setAdapter(tClassScehdule);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}