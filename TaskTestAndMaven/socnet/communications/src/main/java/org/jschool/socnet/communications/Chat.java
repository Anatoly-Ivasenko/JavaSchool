package org.jschool.socnet.communications;

import java.util.List;

public interface Chat {
    public static final int PRIVATE = 1;
    public static final int WALL = 2;
    public static final int CONVERSATION = 3;

    List<Message> getAllChat(int chatId);

    List<Message> getTailOfChat(int chatId);
}
