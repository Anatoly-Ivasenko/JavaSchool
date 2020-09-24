package org.jschool.socnet.core;

import java.util.List;

public interface User {

    User(String text);

    void createNewUser();

    User getUser(int id);

    void inviteFriendship(User user);

    void banUser(User user);

    void createMessage(String textMessage, Chat destinationChat);

    void uploadMedia(File mediaFile, MediaType mediaType, MediaDestination mediaDestination);

    void uploadMedia(File mediaFile, MediaType mediaType, MediaDestination mediaDestination, List<User> userTags);

    /**
     * Возвращает информацию о пользователе + ссылки на его медиа + сслыки на его чаты
     * @return
     */
    UserProfile getProfile();
}
