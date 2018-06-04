package com.dlamloi.MAD.home.chat;

import com.dlamloi.MAD.model.ChatMessage;
import com.dlamloi.MAD.repo.FirebaseRepositoryManager;
import com.dlamloi.MAD.firebasemanager.FirebaseAuthenticationManager;
import com.dlamloi.MAD.firebasemanager.FirebaseCallbackManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Don on 27/05/2018.
 */

/**
 * This class deals with the presentation logic and calls the appropriate class for data storage
 */
public class MessagePresenter implements MessageContract.Presenter, MessageContract.newMessageListener {

    private ArrayList<ChatMessage> mChatMessages = new ArrayList<>();

    private final MessageContract.View mView;
    private FirebaseRepositoryManager mFirebaseRepositoryManager;
    private FirebaseAuthenticationManager mFirebaseAuthenticationManager;
    private FirebaseCallbackManager mFirebaseCallbackManager;

    /**
     * Creates a new instance of the message presenter
     *
     * @param view    the view that this presenter is moderating
     * @param groupId the id of the group the user is in
     */
    public MessagePresenter(MessageContract.View view, String groupId) {
        mView = view;
        mFirebaseRepositoryManager = new FirebaseRepositoryManager(groupId);
        mFirebaseAuthenticationManager = new FirebaseAuthenticationManager();
        mFirebaseCallbackManager = new FirebaseCallbackManager(groupId);
        mFirebaseCallbackManager.attachMessageListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(String messageText) {
        if (!messageText.isEmpty()) {
            mView.clearMessageBox();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy (hh:mma)");
            String time = dateFormat.format(calendar.getTime());
            String username = mFirebaseAuthenticationManager.getCurrentUserEmail();
            ChatMessage message = new ChatMessage(time, username, messageText);
            mFirebaseRepositoryManager.sendMessage(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNewMessage(ChatMessage message) {
        mChatMessages.add(message);
        mView.populateRecyclerView(mChatMessages);
    }
}
