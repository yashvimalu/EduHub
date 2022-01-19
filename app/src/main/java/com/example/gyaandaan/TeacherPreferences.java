package com.example.gyaandaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class TeacherPreferences extends AppCompatActivity {

    TextView subjects,std,board;
    boolean[] selectedLanguage, selectedStandard,selectedBoard;
    ArrayList<Integer> langList = new ArrayList<>();
    ArrayList<Integer> langList2 = new ArrayList<>();
    ArrayList<Integer> langList3 = new ArrayList<>();
    String[] langArray = {"Maths","Science","English","Hindi","Social Studies","Drawing"};
    String[] stdArray = {"1","2","3","4","5","6","7","8","9","10"};
    String[] boardArray = {"CBSE","ICSE","State Board"};
    String username;
    DatabaseReference reference;
    AppCompatButton update;
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder BoardStringBuilder = new StringBuilder();
    StringBuilder STDstringBuilder = new StringBuilder();
    TextInputLayout hours;
    TextInputEditText hours2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_preferences);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        subjects = findViewById(R.id.subjects);
        update = findViewById(R.id.update);
        board=findViewById(R.id.board);
        hours = findViewById(R.id.hours);
        std = findViewById(R.id.standard);
        hours2 = findViewById(R.id.hours2);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        username = extras.getString("username");

        // initialize selected language array
        selectedLanguage = new boolean[langArray.length];
        selectedStandard = new boolean[stdArray.length];
        selectedBoard = new boolean[boardArray.length];

        subjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherPreferences.this);

                // set title
                builder.setTitle("Select Language");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        stringBuilder.setLength(0);
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        subjects.setText(stringBuilder.toString());
                        System.out.println("Selected"+stringBuilder);

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            subjects.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherPreferences.this);
                builder.setTitle("Select Board");
                builder.setCancelable(false);

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
                        for (int j = 0; j < langList3.size(); j++) {
                            BoardStringBuilder.append(boardArray[langList3.get(j)]);
                            if (j != langList3.size() - 1) {
                                BoardStringBuilder.append(", ");
                            }
                        }
                        board.setText(BoardStringBuilder.toString());
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
                        for (int j = 0; j < selectedBoard.length; j++) {
                            selectedBoard[j] = false;
                            langList3.clear();
                            board.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        std.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherPreferences.this);
                builder.setTitle("Select Standard");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(stdArray, selectedStandard, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        STDstringBuilder.setLength(0);
                        if (b) {

                            langList2.add(i);
                            Collections.sort(langList2);
                        } else {
                            langList2.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < langList2.size(); j++) {
                            STDstringBuilder.append(stdArray[langList2.get(j)]);
                            if (j != langList2.size() - 1) {
                                STDstringBuilder.append(", ");
                            }
                        }
                        std.setText(STDstringBuilder.toString());
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
                            langList2.clear();
                            std.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(view);
            }
        });
    }
    public void update(View view){
        String subjectChosen = stringBuilder.toString();
        String stdChosen = STDstringBuilder.toString();
        String boardChosen = BoardStringBuilder.toString();
        if (subjectChosen.equals(""))
        {
            Toast.makeText(TeacherPreferences.this,"Choose Subjects",Toast.LENGTH_LONG).show();
            subjects.requestFocus();
        }

        if (stdChosen.equals(""))
        {
            Toast.makeText(TeacherPreferences.this,"Choose Standard",Toast.LENGTH_LONG).show();
            std.requestFocus();
        }
        if (hours.equals(""))
        {
            Toast.makeText(TeacherPreferences.this,"Choose Hours",Toast.LENGTH_LONG).show();
            hours.requestFocus();
        }

        if (boardChosen.equals(""))
        {
            Toast.makeText(TeacherPreferences.this,"Choose Board",Toast.LENGTH_LONG).show();
            board.requestFocus();
        }
        reference = FirebaseDatabase.getInstance("https://gyandaan-25d02-default-rtdb.firebaseio.com/").getReference().child("Teacher");
        reference.child(username).child("subjects").setValue(subjectChosen);
        reference.child(username).child("standard").setValue(stdChosen);
        reference.child(username).child("hours").setValue(hours.getEditText().getText().toString());
        reference.child(username).child("board").setValue(boardChosen);
    }
}