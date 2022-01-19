package com.example.gyaandaan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyViewHolder> {

    Context context;
    ArrayList<Teacher> list;
    String username,subjectChosen,stdChosen,boardChosen;

    public void setSubjectChosen(String subjectChosen) {
        this.subjectChosen = subjectChosen;
    }
    public void setBoardChosen(String boardChosen) {
        this.boardChosen = boardChosen;
    }


    public TeacherAdapter(Context context, ArrayList<Teacher> list, String username) {
        this.context = context;
        this.list = list;
        this.username = username;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance("https://gyandaan-25d02-default-rtdb.firebaseio.com/").getReference().child("Teacher-Student");
        Teacher t1 = list.get(position);
        holder.name.setText(t1.getName());
        holder.subject.setText(t1.getSubjects());
        holder.std.setText(t1.getStandard());
        holder.board.setText(t1.getBoard());

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Message.class);
                intent.putExtra("sender",username);
                intent.putExtra("receiver",t1.getName());
                view.getContext().startActivity(intent);
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StudentTeacher st1 = new StudentTeacher(username,t1.getName(),subjectChosen,stdChosen,boardChosen);
                reference.push().setValue(st1);
                Toast.makeText(view.getContext(),"Teacher added",Toast.LENGTH_LONG).show();
                holder.add.setVisibility(view.GONE);

            }
        });
    }
    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,subject,std,board;
        AppCompatButton contact,add;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            contact = itemView.findViewById(R.id.contact);
            add = itemView.findViewById(R.id.add);
            name = itemView.findViewById(R.id.teacher_name);
            subject = itemView.findViewById(R.id.subjects);
            std = itemView.findViewById(R.id.standard);
            board=itemView.findViewById(R.id.board);
        }
    }
}