package com.example.gyaandaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TClassAdapter extends RecyclerView.Adapter<TClassAdapter.ViewHolder> {

    private Context context;
    public String student_name, user_name, std, subj,link,time,board;

    public TClassAdapter(Context context, String student_name, String user_name, String std, String subject,String link,String time,String board) {
        this.context = context;
        this.student_name = student_name;
        this.user_name = user_name;
        this.std = std;
        this.board = board;
        this.subj = subject;
        this.link = link;
        this.time = time;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.teacher_class, parent, false);
        return new TClassAdapter.ViewHolder(v);
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
        holder.link.setText(link);
        holder.time.setText(time);
        holder.board.setText(board);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView student_name, std, subject,link,time,board;

        public ViewHolder(View itemView) {
            super(itemView);
            student_name = itemView.findViewById(R.id.student_name);
            std = itemView.findViewById(R.id.std);
            subject = itemView.findViewById(R.id.subject);
            board = itemView.findViewById(R.id.board);
            link = itemView.findViewById(R.id.link);
            time = itemView.findViewById(R.id.time);
        }
    }
}