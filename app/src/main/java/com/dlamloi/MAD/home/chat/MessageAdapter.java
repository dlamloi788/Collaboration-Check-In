package com.dlamloi.MAD.home.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.model.ChatMessage;

import java.util.ArrayList;

/**
 * Created by Don on 27/05/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<ChatMessage> mMessages;

    public MessageAdapter(ArrayList<ChatMessage> chatMessages) {
        mMessages = chatMessages;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView messageComposerTv;
        public TextView messageTextTv;
        public TextView messageTimeSentTv;


        public ViewHolder(View itemView) {
            super(itemView);
            messageComposerTv = itemView.findViewById(R.id.message_composer_textview);
            messageTextTv = itemView.findViewById(R.id.message_text_textview);
            messageTimeSentTv = itemView.findViewById(R.id.message_time_sent_textview);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.message_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage chatMessage = mMessages.get(position);
        holder.messageComposerTv.setText(chatMessage.getComposer());
        holder.messageTextTv.setText(chatMessage.getMessage());
        holder.messageTimeSentTv.setText(chatMessage.getTimeSent());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }


}
