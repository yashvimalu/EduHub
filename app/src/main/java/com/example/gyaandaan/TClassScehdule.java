package com.example.gyaandaan;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TClassScehdule extends RecyclerView.Adapter<TClassScehdule.ViewHolder> {

    private Context context;
    public String student_name,user_name,std,subj,board;

    public TClassScehdule(Context context,String student_name, String user_name,String std,String subject, String board) {
        this.context = context;
        this.student_name=student_name;
        this.user_name = user_name;
        this.std = std;
        this.subj = subject;
        this.board = board;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.schedule_class,parent,false);
        return new TClassScehdule.ViewHolder(v);
    }
    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.student_name.setText(student_name);
        holder.subject.setText(subj);
        holder.std.setText(std);
        holder.board.setText(board);

        holder.google_meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Opening Google Meet",Toast.LENGTH_LONG).show();
                Uri uri = Uri.parse("https://meet.google.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                view.getContext().startActivity(intent);
            }
        });

        holder.zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Opening Zoom",Toast.LENGTH_LONG).show();
                Uri uri = Uri.parse("https://zoom.us"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                view.getContext().startActivity(intent);
            }
        });

        holder.schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = holder.link.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Teacher-Student");
                String time = holder.select_time.getText().toString();
                Log.e("TAG", "**link"+link );
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot:snapshot.getChildren())
                        {
                            if(user_name.toLowerCase().equals(dataSnapshot.child("teacher_name").getValue().toString().toLowerCase()) &&
                                    student_name.toLowerCase().equals(dataSnapshot.child("student_name").getValue().toString().toLowerCase()) )
                            {
                                reference.child(dataSnapshot.getKey()).child("link").setValue(link);
                                reference.child(dataSnapshot.getKey()).child("time").setValue(time);
                            }
                        }
                        Toast.makeText(view.getContext(),"Class scheduled",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        holder.select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog _timePickerDialog;
                int hourOfDay=2;
                int minute=2;
                boolean is24HourView=true;

                //Theme_Holo_Light_Dialog
                //Theme_Holo_Light_DarkActionBar  //*Top Position
                _timePickerDialog=new TimePickerDialog(view.getContext(),android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        //*Return values
                        holder.select_time.setText(i + ":" + i1);
                        Toast.makeText(view.getContext(), "i=" + i + " i1=" + i1, Toast.LENGTH_SHORT).show();

                    }
                },hourOfDay,minute,is24HourView);
                _timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                _timePickerDialog.setTitle("Select a Time");
                _timePickerDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView student_name,std,subject,select_time,board;
        ImageButton google_meet,zoom;
        AppCompatButton schedule;
        EditText link;
        public ViewHolder(View itemView){
            super(itemView);
            student_name = itemView.findViewById(R.id.student_name);
            google_meet = itemView.findViewById(R.id.googleMeet);
            zoom = itemView.findViewById(R.id.zoom);
            schedule = itemView.findViewById(R.id.schedule);
            link = itemView.findViewById(R.id.link);
            std = itemView.findViewById(R.id.std);
            subject = itemView.findViewById(R.id.subject);
            select_time = itemView.findViewById(R.id.select_time);
            board = itemView.findViewById(R.id.board);
        }
    }
}
