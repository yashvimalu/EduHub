package com.example.gyaandaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Teacher_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView nav_view;
    Button logout;
    CardView pref,sche,students,classes;
    String username,table_name;
    AppCompatButton chat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__dashboard);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        pref = findViewById(R.id.preferences);
        sche = findViewById(R.id.schedule);
        students = findViewById(R.id.your_students);
        chat = findViewById(R.id.chat);
        classes = findViewById(R.id.your_classes);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");
        table_name = extras.getString("table_name");

        Log.e("TAG", "username"+username );
        nav_view.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Teacher_Dashboard.this, Welcome.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
        nav_view.bringToFront();
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Teacher_Dashboard.this,TeacherPreferences.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        sche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Teacher_Dashboard.this,TeacherSchedule.class);
                intent.putExtra("user_name",username);
                startActivity(intent);
            }
        });

        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Teacher_Dashboard.this,YourStudents.class);
                intent.putExtra("user_name",username);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Teacher_Dashboard.this, ChatTeacher.class);
                intent.putExtra("sender",username);
                startActivity(intent);
            }
        });

        classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Teacher_Dashboard.this,TeacherClasses.class);
                intent.putExtra("user_name",username);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.profile:
                intent = new Intent(Teacher_Dashboard.this, EditProfile.class);
                intent.putExtra("username",username);
                intent.putExtra("table_name",table_name);
                startActivity(intent);
                break;
            case R.id.your_pref:
                intent = new Intent(Teacher_Dashboard.this,TeacherPreferences.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;

            case R.id.your_stu:
                intent = new Intent(Teacher_Dashboard.this,YourStudents.class);
                intent.putExtra("user_name",username);
                startActivity(intent);
                break;
            case R.id.schedule:
                intent = new Intent(Teacher_Dashboard.this,TeacherSchedule.class);
                intent.putExtra("user_name",username);
                startActivity(intent);
                break;
            case R.id.your_class:
                intent = new Intent(Teacher_Dashboard.this,TeacherClasses.class);
                intent.putExtra("user_name",username);
                startActivity(intent);
                break;

            case  R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(Teacher_Dashboard.this, Welcome.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.drawer_menu_stu, menu);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}