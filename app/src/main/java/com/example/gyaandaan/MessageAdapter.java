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

public class MessageAdapter extends RecyclerView.Adapter {

    public static final int MSG_TYPE_LEFT=2;
    public static final int MSG_TYPE_RIGHT=1;
    private Context context;
    private List<ChatModal> mChat;
    private String sender,receiver;

    public MessageAdapter(Context context, List<ChatModal> mChat,String sender, String receiver) {
        this.context = context;
        this.mChat = mChat;
        this.sender = sender;
        this.receiver = receiver;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == MSG_TYPE_RIGHT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_right, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == MSG_TYPE_LEFT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_left, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModal message = (ChatModal) mChat.get(position);

        switch (holder.getItemViewType()) {
            case MSG_TYPE_RIGHT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case MSG_TYPE_LEFT:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.show_message);
        }
        void bind(ChatModal message) {
            messageText.setText(message.getMessage());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.show_message);
        }
        void bind(ChatModal message) {
            messageText.setText(message.getMessage());
        }
    }

//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v;
//        if (viewType == MSG_TYPE_RIGHT) {
//            v = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
//            Log.d("TAG", "**right");
//        }
//        else{
//            v = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
//            Log.d("TAG", "**left");
//        }
//        return new MessageAdapter.ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        ChatModal chat = mChat.get(position);
//        Log.d("TAG", "** Message is"+chat.getMessage());
//        holder.show_message.setText(chat.getMessage());
//    }

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