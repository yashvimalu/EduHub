package com.example.gyaandaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class FindTeacher extends AppCompatActivity {

    RecyclerView r1;
    DatabaseReference reference;
    TeacherAdapter teacherAdapter;
    ArrayList<Teacher> list;
    AppCompatButton search;
    TextView subjects,std,board;
    boolean[] selectedLanguage, selectedStandard,selectedBoard;
    ArrayList<Integer> langList = new ArrayList<>();
    ArrayList<Integer> langList2 = new ArrayList<>();
    ArrayList<Integer> langList3 = new ArrayList<>();
    String[] langArray = {"Maths","Science","English","Hindi","Social Studies","Drawing"};
    String[] stdArray = {"1","2","3","4","5","6","7","8","9","10"};
    String[] boardArray = {"CBSE","ICSE","State Board"};
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder STDstringBuilder = new StringBuilder();
    StringBuilder BoardStringBuilder = new StringBuilder();
    String subjectTeacher,stdTeacher,username,stdChose,subjChosen,BoardChosen;
    Teacher t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_teacher);

        r1 = findViewById(R.id.teacher_info);
        r1.setHasFixedSize(true);
        r1.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");

        list = new ArrayList<>();
        list.clear();
        teacherAdapter = new TeacherAdapter(this,list,username);

        r1.setAdapter(teacherAdapter);

        reference = FirebaseDatabase.getInstance("https://gyandaan-25d02-default-rtdb.firebaseio.com/").getReference().child("Teacher");
        subjects = findViewById(R.id.subjects);
        std = findViewById(R.id.standard);
        search = findViewById(R.id.search);
        board = findViewById(R.id.board);

        selectedLanguage = new boolean[langArray.length];
        selectedStandard = new boolean[stdArray.length];
        selectedBoard = new boolean[boardArray.length];

        subjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FindTeacher.this);
                builder.setTitle("Select Language");
                builder.setCancelable(false);

                for (int j = 0; j < selectedLanguage.length; j++) {
                    selectedLanguage[j] = false;
                    langList2.clear();
                    subjects.setText("");
                }

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        stringBuilder.setLength(0);
                        if (b) {
                            langList2.add(i);
                            Collections.sort(langList2);
                            Log.e("TAG", "langlist1 "+langList2 );
                        } else {
                            Log.e("TAG", "langlist2 "+langList2 );
                            langList2.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < langList2.size(); j++)
                        {
                            stringBuilder.append(langArray[langList2.get(j)]);
                            if (j != langList2.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        subjects.setText(stringBuilder.toString());
                        subjChosen = stringBuilder.toString();
                        teacherAdapter.setSubjectChosen(subjChosen);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            selectedLanguage[j] = false;
                            langList2.clear();
                            subjects.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        std.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FindTeacher.this);
                builder.setTitle("Select Standard");
                builder.setCancelable(false);

                for (int j = 0; j < selectedStandard.length; j++) {
                    selectedStandard[j] = false;
                    langList.clear();
                    std.setText("");
                }

                builder.setMultiChoiceItems(stdArray, selectedStandard, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b)
                    {
                        STDstringBuilder.setLength(0);
                        if (b) {
                            langList.add(i);
                            Collections.sort(langList);
                        } else {
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < langList.size(); j++)
                        {
                            STDstringBuilder.append(stdArray[langList.get(j)]);
                            if (j != langList.size() - 1) {
                                STDstringBuilder.append(", ");
                            }
                        }
                        std.setText(STDstringBuilder.toString());
                        stdChose = STDstringBuilder.toString();
                        teacherAdapter.stdChosen=stdChose;
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < selectedStandard.length; j++) {
                            selectedStandard[j] = false;
                            langList.clear();
                            std.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FindTeacher.this);
                builder.setTitle("Select Board");
                builder.setCancelable(false);

                for (int j = 0; j < selectedBoard.length; j++) {
                    selectedBoard[j] = false;
                    langList3.clear();
                    board.setText("");
                }

                builder.setMultiChoiceItems(boardArray, selectedBoard, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        BoardStringBuilder.setLength(0);
                        if (b) {
                            langList3.add(i);
                            Collections.sort(langList3);

                        } else {

                            langList3.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < langList3.size(); j++)
                        {
                            BoardStringBuilder.append(boardArray[langList3.get(j)]);
                            if (j != langList3.size() - 1) {
                                BoardStringBuilder.append(", ");
                            }
                        }
                        board.setText(BoardStringBuilder.toString());
                        BoardChosen = BoardStringBuilder.toString();
                        teacherAdapter.setBoardChosen(BoardChosen);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            selectedLanguage[j] = false;
                            langList2.clear();
                            subjects.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                            subjectTeacher=dataSnapshot.child("subjects").getValue().toString();
                            stdTeacher=dataSnapshot.child("standard").getValue().toString();

                            if(subjectTeacher.contains(subjChosen) && stdTeacher.contains(stdChose)) {
                                t1 = dataSnapshot.getValue(Teacher.class);
                                list.add(t1);
                            }
                        }

                        if(list.size()==0)
                        {
                            Toast.makeText(FindTeacher.this,"No teacher found",Toast.LENGTH_LONG).show();
                        }
                        teacherAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}