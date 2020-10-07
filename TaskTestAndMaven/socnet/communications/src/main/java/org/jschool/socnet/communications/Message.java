package org.jschool.socnet.communications;

import org.jschool.socnet.media.Media;
import java.util.List;

/**
 * Интрефейс сообщения, предназначен для обработки информации об отправляемых сообщениях
 *
 * Объект предположительно должен содержать текст сообщения, информацию об авторе,
 * ссылки на медиа включенные в сообщение.
 *
 */
public interface Message {

    void replaceMessage(Message newMessage);

    String getTextOfMessage();

    List<Media> getReferencesMedia();

}
