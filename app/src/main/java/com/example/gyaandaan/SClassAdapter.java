package com.example.gyaandaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SClassAdapter extends RecyclerView.Adapter<SClassAdapter.ViewHolder> {

    private Context context;
    public String teacher_name, user_name, std, subj, link,time,board;

    public SClassAdapter(Context context, String teacher_name, String user_name, String std, String subject, String link,String time,String board) {
        this.context = context;
        this.teacher_name = teacher_name;
        this.user_name = user_name;
        this.std = std;
        this.subj = subject;
        this.time = time;
        this.board = board;
        this.link = link;
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
        holder.teacher_name.setText(teacher_name);
        holder.subject.setText(subj);
        holder.std.setText(std);
        holder.link.setText(link);
        holder.time.setText(time);
        holder.board.setText(board);
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