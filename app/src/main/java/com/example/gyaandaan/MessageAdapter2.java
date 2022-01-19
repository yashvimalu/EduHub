package com.example.gyaandaan;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter2 extends RecyclerView.Adapter<MessageAdapter2.ViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=0;
    private Context context;
    private List<ChatModal> mChat;
    private String sender,receiver;

    public MessageAdapter2(Context context, List<ChatModal> mChat,String sender, String receiver) {
        this.context = context;
        this.mChat = mChat;
        this.sender = sender;
        this.receiver = receiver;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == MSG_TYPE_RIGHT) {
            v = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            Log.d("TAG", "**right");
        }
        else{
            v = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            Log.d("TAG", "**left");
        }
        return new MessageAdapter2.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChatModal chat = mChat.get(position);
        Log.d("TAG", "** Message is"+chat.getMessage());
        holder.show_message.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;

        public ViewHolder(View itemView){
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if(mChat.get(position).getReceiver().equals(sender))  {
            return MSG_TYPE_LEFT;
        }
        else
        {
            return MSG_TYPE_RIGHT;
        }
    }
}