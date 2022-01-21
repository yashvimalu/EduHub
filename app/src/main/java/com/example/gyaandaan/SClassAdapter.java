package com.example.gyaandaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SClassAdapter extends RecyclerView.Adapter<SClassAdapter.ViewHolder> {

    private Context context;
    ArrayList<ClassModal> list;
    public String teacher_name, user_name, std, subj, link,time,board;

    public SClassAdapter(Context context,ArrayList<ClassModal>list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.student_class, parent, false);
        return new SClassAdapter.ViewHolder(v);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        ClassModal cm1 = list.get(position);
        holder.teacher_name.setText(cm1.getTeacher_name());
        holder.subject.setText(cm1.getSubject());
        holder.std.setText(cm1.getStandard());
        holder.link.setText(cm1.getLink());
        holder.time.setText(cm1.getTime());
        holder.board.setText(cm1.getBoard());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView teacher_name, std, subject, link,time,board;

        public ViewHolder(View itemView) {
            super(itemView);
            teacher_name = itemView.findViewById(R.id.teacher_name);
            std = itemView.findViewById(R.id.std);
            subject = itemView.findViewById(R.id.subject);
            link = itemView.findViewById(R.id.link);
            time = itemView.findViewById(R.id.time);
            board = itemView.findViewById(R.id.board);
        }
    }
}