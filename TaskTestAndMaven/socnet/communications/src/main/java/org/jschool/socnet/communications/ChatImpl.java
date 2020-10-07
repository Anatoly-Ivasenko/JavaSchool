package org.jschool.socnet.communications;

import org.jschool.socnet.core.User;

import java.util.ArrayList;
import java.util.List;

public class ChatImpl implements Chat {
    private static final int TAIL_LENGTH = 10;

    //@GeneratedValue  -  значение генерируется при создании
    private int id;
    private User host;
    private ChatType type;
    private List<Message> messages;
    private List<User> powerUsers;
    private List<User> users;
    private List<User> candidates;


    ChatImpl() {
    }

    ChatImpl(User host, ChatType type) {
        this.host = host;
        this.type = type;
        this.messages = new ArrayList<>();
        this.powerUsers = new ArrayList<>();
        this.users = new ArrayList<>();
        this.candidates = new ArrayList<>();
    }

    public User getHost() {
        return host;
    }

    public ChatType getType() {
        return type;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<User> getPowerUsers() {
        return powerUsers;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public void putMessage(Message message) {
        messages.add(message);
    }

    @Override
    public List<Message> getAllChat() {
        return messages;
    }

    @Override
    public List<Message> getTailOfChat() {
        int size = messages.size();
        return messages.subList(size - ChatImpl.TAIL_LENGTH, size - 1);
    }

    @Override
    public Message getLastMessage() {
        return messages.get(messages.size()-1);
    }
}
