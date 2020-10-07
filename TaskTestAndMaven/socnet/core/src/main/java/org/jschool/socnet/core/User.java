package org.jschool.socnet.core;

import org.jschool.socnet.communications.Chat;
import org.jschool.socnet.communications.Message;
import org.jschool.socnet.media.Media;

import java.io.File;
import java.util.List;

/**
 * Интерфейс пользователя, представляет модель пользвателя соцсети.
 *
 *
 */

public interface User {

    void offerFriendship(User user);

    void banUser(User user);

    Chat createChat();

    void inviteToChat(User user, Chat chat);

    void joinToChat(Chat chat);

    Message createMessage(String textMessage, Chat destinationChat);
    // return new MessageImpl (textMessage, destinationChat, this);

    Message createMessage(String textMessage, List<Media> medias, Chat destinationChat);
    // return new MessageImpl (textMessage, medias, destinationChat, this);

    void uploadMedia(File mediaFile);

    void uploadMedia(File mediaFile, List<User> userTags);

    /**
     * Возвращает информацию о пользователе + ссылки на его медиа + сслыки на его чаты
     * @return
     */
    List<Object> getProfile();
}
