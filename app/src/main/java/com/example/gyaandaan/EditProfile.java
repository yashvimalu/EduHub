package com.example.gyaandaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {

    FirebaseDatabase rootFD;
    DatabaseReference reference;
    String username;
    String table_name = "apple";
    TextInputEditText name2, email2, mobile2, pwd2, confpwd2;
    TextInputLayout name,email,mobile,pwd,confpwd;
    String _NAME,_EMAIL,_MOBILE,_PWD;
    ProgressDialog TempDialog;
    AppCompatButton update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        name2 = findViewById(R.id.name2);
        email2 = findViewById(R.id.email2);
        mobile2 = findViewById(R.id.mobileNo2);
        pwd2 = findViewById(R.id.password2);
        confpwd2 = findViewById(R.id.confirm_password2);
        update = findViewById(R.id.update);

        name = findViewById(R.id.name);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.mobileNo);
        pwd=findViewById(R.id.password);
        confpwd=findViewById(R.id.confirm_password);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");
        table_name = extras.getString("table_name");
        Log.d("TAG", "Table name received "+table_name);

        rootFD = FirebaseDatabase.getInstance("https://gyandaan-25d02-default-rtdb.firebaseio.com/");
        reference = rootFD.getReference().child(table_name);

        TempDialog = new ProgressDialog(EditProfile.this);
        TempDialog.setMessage("Please Wait");
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.setCancelable(false);
        TempDialog.show();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()) {

                    TempDialog.dismiss();
                    name2.setText(username);
                    email2.setText(dataSnapshot.child("email_id").getValue().toString());
                    mobile2.setText(dataSnapshot.child("phone").getValue().toString());
                    pwd2.setText(dataSnapshot.child("pwd").getValue().toString());
                    confpwd2.setText(dataSnapshot.child("pwd").getValue().toString());

                    _NAME = username;
                    _EMAIL=dataSnapshot.child("email_id").getValue().toString();
                    _MOBILE=dataSnapshot.child("phone").getValue().toString();
                    _PWD = dataSnapshot.child("pwd").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    update(view);
            }
        });

    }

    public void update(View view){
        if(isNameChanged() || isEmailChanged() || isPhoneChanged() || isPasswordChanged())
        {
            Toast.makeText(this,"Details are updated",Toast.LENGTH_LONG).show();
        }
    }

    private boolean isPasswordChanged() {

        if(_PWD.equals(pwd.getEditText().getText().toString())){
            reference.child(_NAME).child("pwd").setValue(pwd.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isPhoneChanged(){

        if(!_MOBILE.equals(mobile.getEditText().getText().toString())){
            reference.child(_NAME).child("phone").setValue(mobile.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isEmailChanged() {

        if(!_EMAIL.equals(email.getEditText().getText().toString())){
            reference.child(_NAME).child("email_id").setValue(email.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isNameChanged() {

        if(!_NAME.toLowerCase().equals(name.getEditText().getText().toString().toLowerCase())){
            reference.child(_NAME).child("name").setValue(name.getEditText().getText().toString());
            return true;
        }
        else{
            return false;
        }
    }
}