package com.example.gyaandaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Welcome extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {
    TextInputLayout uname, password;
    TextInputEditText uname2, password2;
    Button login, sign_up, fp;
    FirebaseDatabase rootFD;
    TextView error;
    DatabaseReference reference, reference2;
    String username, pwd, user_name, pass_word, table_name = "apple";
    ProgressDialog TempDialog;
    int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        uname = findViewById(R.id.uname);
        uname2 = findViewById(R.id.uname2);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        login = findViewById(R.id.login);
        sign_up = findViewById(R.id.signup);
        error = findViewById(R.id.error);
        fp = findViewById(R.id.fp);

        rootFD = FirebaseDatabase.getInstance("https://gyandaan-25d02-default-rtdb.firebaseio.com/");
        reference = rootFD.getReference().child("Users");

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = uname2.getText().toString().toLowerCase();
                if (username.equals("")) {
                    Toast.makeText(Welcome.this, "Enter username", Toast.LENGTH_LONG).show();
                    uname.requestFocus();
                } else {
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                user_name = dataSnapshot.child("fullname").getValue().toString().toLowerCase();
                                if (!user_name.equals(username)) {
                                    Toast.makeText(Welcome.this, "User does not exist. Create account", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Welcome.this, Signup.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(Welcome.this, Forget_Password.class);
                                    intent.putExtra("user_name", uname.getEditText().toString());
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkConnection();

                username = uname2.getText().toString().toLowerCase();
                pwd = password2.getText().toString();
                rootFD = FirebaseDatabase.getInstance("https://gyandaan-25d02-default-rtdb.firebaseio.com/");
                reference = rootFD.getReference().child("Users");

                TempDialog = new ProgressDialog(Welcome.this);
                TempDialog.setMessage("Please Wait");
                TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                TempDialog.setCancelable(false);
                TempDialog.show();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            user_name = dataSnapshot.child("fullname").getValue().toString().toLowerCase();

                            if (!user_name.equals(username)) {
                                Log.d("TAG", "condtion not match flag is "+flag);
                                continue;
                            }
                            else
                                {
                                table_name = dataSnapshot.child("tablename").getValue().toString();

                                reference2 = rootFD.getReference().child(table_name);
                                reference2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                            user_name = dataSnapshot.child("name").getValue().toString();
                                            pass_word = dataSnapshot.child("pwd").getValue().toString();

                                            if (user_name.toLowerCase().equals(username) && pass_word.equals(pwd)) {
                                                if (table_name.equals("Student")) {
                                                    TempDialog.dismiss();
                                                    flag =0;
                                                    Log.d("TAG", "condtion match flag is "+flag);
                                                    Toast.makeText(Welcome.this, "Login Successful " + username, Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(Welcome.this, Student_Dashboard.class);
                                                    Log.d("TAG", "Table name sent " + table_name);
                                                    intent.putExtra("user_name", user_name);
                                                    intent.putExtra("table_name", table_name);
                                                    startActivity(intent);
                                                    finish();

                                                } else {
                                                    TempDialog.dismiss();
                                                    flag =0;
                                                    Log.d("TAG", "condtion match flag is "+flag);
                                                    Toast.makeText(Welcome.this, "Login Successful " + username, Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(Welcome.this, Teacher_Dashboard.class);
                                                    intent.putExtra("user_name", user_name);
                                                    intent.putExtra("table_name", table_name);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            } else if(user_name.equals(username) || pass_word.equals(pwd)){
                                                TempDialog.dismiss();
                                                error.setText("Username or Password incorrect");
                                                error.setTextColor(Color.RED);
                                                finish();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
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

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, Signup.class);
                startActivity(intent);
            }
        });
    }

    private void checkConnection() {

        // initialize intent filter
        IntentFilter intentFilter = new IntentFilter();

        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        // register receiver
        registerReceiver(new ConnectionReceiver(), intentFilter);

        // Initialize listener
        ConnectionReceiver.Listener = this;

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // get connection status
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        // display snack bar
        showSnackBar(isConnected);
    }

    private void showSnackBar(boolean isConnected) {

        // initialize color and message
        String message;
        int color;

        // check condition
        if (isConnected) {

            // when internet is connected
            // set message
            message = "Connected to Internet";

            // set text color
            color = Color.WHITE;

        } else {

            // when internet
            // is disconnected
            // set message
            message = "Not Connected to Internet";

            // set text color
            color = Color.RED;
        }

        // initialize snack bar
        Snackbar snackbar = Snackbar.make(findViewById(R.id.login), message, Snackbar.LENGTH_LONG);

        // initialize view
        View view = snackbar.getView();

        // Assign variable
        TextView textView = view.findViewById(R.id.snackbar_text);

        // set text color
        textView.setTextColor(color);

        // show snack bar
        snackbar.show();
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        // display snack bar
        showSnackBar(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // call method
        checkConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // call method
        checkConnection();
    }
}