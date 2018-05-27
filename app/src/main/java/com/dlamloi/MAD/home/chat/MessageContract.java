package com.dlamloi.MAD.home.chat;

import com.dlamloi.MAD.base.BaseView;
import com.dlamloi.MAD.model.ChatMessage;

/**
 * Created by Don on 27/05/2018.
 */

public interface MessageContract {

    interface View extends BaseView<ChatMessage> {

        void clearMessageBox();
    }

    interface Presenter {

        void sendMessage(String s);
    }

    interface newMessageListener {

        void onNewMessage(ChatMessage message);

    }


}
