package com.example.gyaandaan;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserChatAdapter extends RecyclerView.Adapter<UserChatAdapter.ViewHolder> {

    private Context context;
    private List<ChatModal> mUsers;
    private String sender;

    public UserChatAdapter(Context context, List<ChatModal> mUsers,String sender) {
        this.context = context;
        this.mUsers = mUsers;
        this.sender = sender;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.chat_user_item,parent,false);
        return new UserChatAdapter.ViewHolder(v);
    }
    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(true);
        ChatModal user = mUsers.get(position);
        if(!sender.equals(user.getSender())){
        holder.username.setText(user.getSender());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "** Message class called from Chat.class");
                Intent intent = new Intent(context,Message.class);
                intent.putExtra("sender",sender);
                intent.putExtra("receiver",user.getSender());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;

        public ViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.username);
        }
    }
}