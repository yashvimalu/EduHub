package com.example.gyaandaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Student_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView nav_view;
    Button logout;
    CardView find_teacher,your_teachers,classes;
    String username,tablename;
    AppCompatButton chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__dashboard);

        find_teacher = findViewById(R.id.find_teacher);
        toolbar = findViewById(R.id.toolbar);
        classes = findViewById(R.id.schedule);
        drawer = findViewById(R.id.drawer_layout);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        chat = findViewById(R.id.chat);
        your_teachers = findViewById(R.id.your_teachers);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");
        tablename = extras.getString("table_name");
        nav_view.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Student_Dashboard.this, Welcome.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
        nav_view.bringToFront();
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        find_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Student_Dashboard.this,FindTeacher.class);
                intent.putExtra("user_name", username);
                intent.putExtra("table_name",tablename);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Student_Dashboard.this, ChatStudent.class);
                intent.putExtra("sender",username);
                intent.putExtra("table_name",tablename);
                startActivity(intent);
            }
        });

        classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Student_Dashboard.this,StudentClasses.class);
                intent.putExtra("student_name",username);
                startActivity(intent);
            }
        });
        your_teachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Student_Dashboard.this,YourTeachers.class);
                intent.putExtra("user_name", username);
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
        switch (menuItem.getItemId()) {
            case R.id.profile:
                Bundle extras = getIntent().getExtras();
                Intent intent = new Intent(Student_Dashboard.this, EditProfile.class);
                intent.putExtra("user_name",username);
                intent.putExtra("table_name",tablename);

                startActivity(intent);
                break;

            case R.id.your_teac:

                Intent intent2 = new Intent(Student_Dashboard.this, YourTeachers.class);
                intent2.putExtra("user_name",username);
                startActivity(intent2);
                break;

            case R.id.find_teac:
                Intent intent3 = new Intent(Student_Dashboard.this, FindTeacher.class);
                intent3.putExtra("user_name",username);
                intent3.putExtra("table_name",tablename);
                startActivity(intent3);
                break;

            case R.id.classes:
                Intent intent4 = new Intent(Student_Dashboard.this,StudentClasses.class);
                intent4.putExtra("student_name",username);
                startActivity(intent4);
                break;

            case  R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(Student_Dashboard.this, Welcome.class);
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