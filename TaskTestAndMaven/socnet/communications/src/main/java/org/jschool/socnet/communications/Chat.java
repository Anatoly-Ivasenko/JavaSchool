package org.jschool.socnet.communications;

import java.util.List;

/**
 * Интерфейс чата, предназначен для реализации логики работы сообщений, стен и групповых чатов.
 *
 * Объект предположительно должен хранить информацию об авторе (хосте), типе чата, участниках.
 *
 */

public interface Chat {
    public static final int PRIVATE = 1;        //Личная переписка tet-a-tet
    public static final int WALL = 2;           //Переписка на стене
    public static final int CONVERSATION = 3;   //Групповой чат

    void putMessage(Message message);

    List<Message> getAllChat();

    List<Message> getTailOfChat();

    Message getLastMessage();
}
