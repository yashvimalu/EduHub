package com.example.gyaandaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ChatStudent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView chat_rv;
    String sender,table_name;
    UserChatAdapter userChatAdapter;
    List<ChatModal> mUsers;
    DatabaseReference reference;
    List<String> usersList;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView nav_view;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_stu);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatStudent.this, Welcome.class);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        nav_view.bringToFront();
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        chat_rv = findViewById(R.id.chat_rv);
        chat_rv.setHasFixedSize(true);
        chat_rv.setLayoutManager(new LinearLayoutManager(this));

        mUsers = new ArrayList<>();

        Bundle extras=getIntent().getExtras();
        sender=extras.getString("sender");
        table_name=extras.getString("table_name");
        mUsers.clear();

        usersList=new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatModal chat = dataSnapshot.getValue(ChatModal.class);

                    if(chat.getSender().toLowerCase().equals(sender.toLowerCase())){
                        if(!usersList.contains(chat.getReceiver().toLowerCase())) {
                            usersList.add(chat.getReceiver().toLowerCase());
                        }
                    }

                    if(chat.getReceiver().toLowerCase().equals(sender.toLowerCase())) {
                        if (!usersList.contains(chat.getSender().toLowerCase())) {
                            usersList.add(chat.getSender().toLowerCase());
                        }
                    }
                }
                Log.e("TAG", "UserList"+usersList );
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void readChats(){

        mUsers=new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ChatModal user = dataSnapshot.getValue(ChatModal.class);

                    for (String name:usersList){
                        if(user.getReceiver().toLowerCase().equals(name.toLowerCase())){
                            if(mUsers.size()!=0) {
                                for (ChatModal user1 : mUsers) {
                                    if (user.getReceiver().toLowerCase().equals(user1.getReceiver().toLowerCase())) {
                                        mUsers.add(user);
                                    }
                                }
                            }
                        }
                            else
                            {
                                mUsers.add(user);
                            }
                        }
                    }userChatAdapter=new UserChatAdapter(ChatStudent.this,mUsers,sender);
                chat_rv.setAdapter(userChatAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                Intent intent = new Intent(ChatStudent.this, EditProfile.class);
                intent.putExtra("user_name",sender);
                intent.putExtra("table_name",table_name);

                startActivity(intent);
                break;

            case R.id.your_teac:

                Intent intent2 = new Intent(ChatStudent.this, YourTeachers.class);
                intent2.putExtra("user_name",sender);
                startActivity(intent2);
                break;

            case R.id.find_teac:
                Intent intent3 = new Intent(ChatStudent.this, FindTeacher.class);
                intent3.putExtra("user_name",sender);
                intent3.putExtra("table_name",table_name);
                startActivity(intent3);
                break;

            case R.id.classes:
                Intent intent4 = new Intent(ChatStudent.this,StudentClasses.class);
                intent4.putExtra("student_name",sender);
                startActivity(intent4);
                break;


            case  R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(ChatStudent.this, Welcome.class);
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