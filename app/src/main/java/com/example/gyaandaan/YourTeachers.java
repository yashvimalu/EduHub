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

public class YourTeachers extends AppCompatActivity {
    RecyclerView r1;
    String username,sender;
    TeacherStudentAdapter teacherStudentAdapter;
    ArrayList<Teacher2> list;
    DatabaseReference reference;
    Teacher2 t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_teachers);

        r1 = findViewById(R.id.teacher_data);
        r1.setHasFixedSize(true);
        r1.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");
        sender = extras.getString("user_name");

        Log.e("TAG", "Your Teachers called " );
        list=new ArrayList<>();

        teacherStudentAdapter = new TeacherStudentAdapter(this,list,sender);
        r1.setAdapter(teacherStudentAdapter);

        reference = FirebaseDatabase.getInstance("https://gyandaan-25d02-default-rtdb.firebaseio.com/").getReference().child("Teacher-Student");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren())
                {
                    Log.e("TAG", "Checking firebase+ "+username );

                    if(username.equals(dataSnapshot.child("student_name").getValue().toString())){
                        t1 = dataSnapshot.getValue(Teacher2.class);
                        Log.d("TAG","@@@ Data "+t1);
                        list.add(t1);
                    }
                }teacherStudentAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}