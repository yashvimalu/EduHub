package com.example.gyaandaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TClassAdapter extends RecyclerView.Adapter<TClassAdapter.ViewHolder> {

    private Context context;
    public String student_name, user_name, std, subj,link,time,board;
    ArrayList<ClassModal> list;


    public TClassAdapter(Context context, ArrayList<ClassModal>list) {
        this.context = context;
        this.list = list;
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
        ClassModal cm1 = list.get(position);
        holder.student_name.setText(cm1.getStudent_name());
        holder.subject.setText(cm1.getSubject());
        holder.std.setText(cm1.getStandard());
        holder.link.setText(cm1.getLink());
        holder.time.setText(cm1.getTime());
        holder.board.setText(cm1.getBoard());
    }

    @Override
    public int getItemCount() {
        return list.size();
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