package com.dlamloi.MAD.home.chat;

import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.ChatMessage;

/**
 * Created by Don on 27/05/2018.
 */

/**
 * This interface is a contract between the presenter and the view
 */
public interface MessageContract {

    /**
     * The associated view in the contract
     */
    interface View extends BaseView<ChatMessage> {

        /**
         * Clears the edittext containing the users message
         */
        void clearMessageBox();
    }


    /**
     * The associated presenter in the contract
     */
    interface Presenter {

        /**
         * Stores a message into the database
         *
         * @param s the message to be stored
         */
        void sendMessage(String s);
    }

    /**
     * A listener for any new messages that are added
     */
    interface newMessageListener {

        /**
         * Notifies the presenter that a new message was added
         *
         * @param message the message that was added
         */
        void onNewMessage(ChatMessage message);

    }


}
