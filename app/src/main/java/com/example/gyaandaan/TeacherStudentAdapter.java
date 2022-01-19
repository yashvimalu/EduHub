package com.example.gyaandaan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class TeacherStudentAdapter extends RecyclerView.Adapter<TeacherStudentAdapter.MyViewHolder> {

    Context context;
    String sender;
    ArrayList<Teacher2> list;

    public TeacherStudentAdapter(Context context, ArrayList<Teacher2> list,String sender) {
        this.context = context;
        this.list = list;
        this.sender = sender;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance("https://gyandaan-25d02-default-rtdb.firebaseio.com/").getReference().child("Teacher-Student");

        Teacher2 t1 = list.get(position);
        holder.name.setText(t1.getTeacher_name());
        holder.subject.setText(t1.getSubject());
        holder.std.setText(t1.getStandard());
        holder.board.setText(t1.getBoard());

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Message.class);
                intent.putExtra("sender",sender);
                intent.putExtra("receiver",t1.getTeacher_name());
                view.getContext().startActivity(intent);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setMessage("Are you sure you want to remove teacher?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                reference.removeValue();
                                Toast.makeText(view.getContext(),"Teacher Removed",Toast.LENGTH_LONG).show();
                                list.remove(position);
                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //StudentTeacher st1 = new StudentTeacher(t1.getStudent_name(),t1.getTeacher_name(),t1.getSubject(),t1.getStandard());
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,subject,std,board;
        AppCompatButton contact,remove;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.teacher_name);
            subject = itemView.findViewById(R.id.subjects);
            std = itemView.findViewById(R.id.standard);
            remove = itemView.findViewById(R.id.remove);
            contact=itemView.findViewById(R.id.contact);
            board = itemView.findViewById(R.id.board);
        }
    }
}