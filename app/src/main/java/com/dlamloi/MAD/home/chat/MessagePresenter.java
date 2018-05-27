package com.dlamloi.MAD.home.chat;

import com.dlamloi.MAD.model.ChatMessage;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;
import com.dlamloi.MAD.utilities.FirebaseAuthenticationManager;
import com.dlamloi.MAD.utilities.FirebaseCallbackManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Don on 27/05/2018.
 */

public class MessagePresenter implements MessageContract.Presenter, MessageContract.newMessageListener {

    private ArrayList<ChatMessage> mChatMessages = new ArrayList<>();

    private final MessageContract.View mView;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;
    private FirebaseCallbackManager mFirebaseCallbackManager;


    public MessagePresenter(MessageContract.View view, String groupId) {
        mView = view;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(groupId);
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager();
        mFirebaseCallbackManager = new FirebaseCallbackManager(groupId);
        mFirebaseCallbackManager.attachMessageListener(this);
    }



    public void sendMessage(String messageText) {
        mView.clearMessageBox();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy (hh:mma)");
        String time = dateFormat.format(calendar.getTime());
        String username = mFirebaseAuthenticationManager.getCurrentUser().getDisplayName();
        ChatMessage message = new ChatMessage(time, username, messageText);
        mFirebaseRepositoryManager.sendMessage(message);
    }

    @Override
    public void onNewMessage(ChatMessage message) {
        mChatMessages.add(message);
        mView.populateRecyclerView(mChatMessages);
    }
}
