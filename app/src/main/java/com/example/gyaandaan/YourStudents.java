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

import java.util.ArrayList;

public class YourStudents extends AppCompatActivity {
    RecyclerView r1;
    String username;
    StudentTeacherAdapter studentTeacherAdapter;
    ArrayList<Teacher2> list;
    DatabaseReference reference;
    Teacher2 t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_students);

        r1 = findViewById(R.id.student_data);
        r1.setHasFixedSize(true);
        r1.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");
        Log.d("TAG", "**Username "+username);
        list=new ArrayList<>();

        studentTeacherAdapter = new StudentTeacherAdapter(this,list,username);
        r1.setAdapter(studentTeacherAdapter);

        reference = FirebaseDatabase.getInstance("https://gyandaan-25d02-default-rtdb.firebaseio.com/").getReference().child("Teacher-Student");

        Log.d("TAG", "Searching fb");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren())
                {
                    if(username.equals(dataSnapshot.child("teacher_name").getValue().toString())){
                        Log.d("TAG", "**Data found");
                        t1 = dataSnapshot.getValue(Teacher2.class);
                        list.add(t1);
                    }
                }studentTeacherAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}