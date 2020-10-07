package org.jschool.socnet.communications;

import java.util.List;

/**
 * Интерфейс чата, предназначен для реализации логики работы сообщений, стен и групповых чатов.
 *
 * Объект предположительно должен хранить информацию об авторе (хосте), типе чата, участниках.
 *
 */

public interface Chat {

    void putMessage(Message message);

    List<Message> getAllChat();

    List<Message> getTailOfChat();

    Message getLastMessage();
}
