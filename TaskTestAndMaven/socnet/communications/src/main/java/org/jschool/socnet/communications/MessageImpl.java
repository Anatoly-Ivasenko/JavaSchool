package org.jschool.socnet.communications;

import org.jschool.socnet.core.User;
import org.jschool.socnet.media.Media;

import java.util.Date;
import java.util.List;

public class MessageImpl implements Message {

    //@GeneratedValue  -  значение генерируется при создании
    private int id;

    private String text;
    private User author;
    private long timeOfCreation;
    private long timeOfModify;
    private List<Media> listOfMedia;

    MessageImpl() {
    }

    MessageImpl(String textMessage, Chat destinationChat, User author) {
        this.text = textMessage;
        this.author = author;
        this.timeOfCreation = new Date().getTime();
        this.timeOfModify = timeOfCreation;
        destinationChat.putMessage(this);
    }

    MessageImpl(String textMessage, List<Media> medias, Chat destinationChat, User author) {
        this.text = textMessage;
        this.author = author;
        this.timeOfCreation = new Date().getTime();
        this.timeOfModify = timeOfCreation;
        this.listOfMedia = medias;
        destinationChat.putMessage(this);
    }

    public long getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    @Override
    public List<Media> getMedias() {
        return listOfMedia;
    }

    public long getTimeOfCreation() {
        return timeOfCreation;
    }

    public long getTimeOfModify() {
        return timeOfModify;
    }

    @Override
    public void replaceMessage(String newTextMessage) {
        this.text = newTextMessage;
        this.timeOfModify = new Date().getTime();
    }

    @Override
    public void replaceMessage(List<Media> newMedias) {
        this.listOfMedia = newMedias;
        this.timeOfModify = new Date().getTime();
    }

    @Override
    public void replaceMessage(String newTextMessage, List<Media> newMedias) {
        this.text = newTextMessage;
        this.listOfMedia = newMedias;
        this.timeOfModify = new Date().getTime();
    }
}
