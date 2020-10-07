package org.jschool.socnet.communications;

import org.jschool.socnet.media.Media;
import java.util.List;

public class MessageImpl implements Message {
    private long messageId;
    private String messageText;
    private long timeOfCreation;
    private long timeOfModify;
    private List<Media> listOfMedia;

    public void replaceMessage(Message newMessage) {
    }

    public String getTextOfMessage() {
        return messageText;
    }

    public List<Media> getReferencesMedia() {
        return listOfMedia;
    }
}
