package com.example.gyaandaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Forget_Password extends AppCompatActivity {

    String username, user_name, table_name;
    FirebaseDatabase rootFD;
    DatabaseReference reference, reference2;
    TextInputEditText pwd2, cnf_password2;
    TextInputLayout pwd, cnf_password;
    AppCompatButton chg_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);

        pwd = findViewById(R.id.pwd);
        pwd2 = findViewById(R.id.pwd2);
        cnf_password = findViewById(R.id.cnf_password);
        cnf_password2 = findViewById(R.id.cnf_password2);
        chg_pwd = findViewById(R.id.changePassword);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");

        rootFD = FirebaseDatabase.getInstance("https://gyandaan-25d02-default-rtdb.firebaseio.com/");
        reference = rootFD.getReference().child("Users");

        chg_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            user_name = dataSnapshot.child("fullname").getValue().toString().toLowerCase();
                            if (user_name.equals(username)) {
                                table_name = dataSnapshot.child("tablename").getValue().toString();
                                reference2 = rootFD.getReference().child(table_name);
                                reference2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            user_name = dataSnapshot.child("name").getValue().toString().toLowerCase();
                                            if (username.equals(user_name)) {
                                                if (pwd.getEditText().getText().toString().equals(cnf_password.getEditText().getText().toString())) {
                                                    reference2.child(username).child("phone").setValue(pwd.getEditText().getText().toString());
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error)  {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}