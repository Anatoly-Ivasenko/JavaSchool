package org.jschool.socnet.communications;

import java.util.List;

public interface Message {

    Message getMessage(int messageId);

    void replaceMessage(int messageId, Message message);

    String getTextOfMessage(Message message);

    String getTextOfMessage(int messageId);

    List<Integer> getReferenceMedia(Message message);

    List<Integer> getReferenceMedia(int messageId);
}
