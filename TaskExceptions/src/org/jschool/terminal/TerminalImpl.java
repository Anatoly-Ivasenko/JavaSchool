package org.jschool.terminal;

import java.util.*;

public class TerminalImpl implements Terminal {
    private final TerminalServer server;
    private final PinValidator pinValidator;
    private final List<String> availableActions;
    private boolean isLogin;
    private boolean isLocked;
    private Long lockTime;
    private String pinCode;
    private int currentAction;
    private Queue<String> messages;

    public TerminalImpl(TerminalServer server, PinValidator pinValidator) {
        this.server = server;
        this.pinValidator = pinValidator;
        this.isLogin = false;
        this.isLocked = false;
        pinCode = "";
        List<String> actionList = new ArrayList<>();
        actionList.add(0, "Авторизация");
        actionList.add(1, "Проверить состояние счета");
        actionList.add(2, "Снять деньги");
        actionList.add(3, "Положить деньги");
        this.availableActions = Collections.unmodifiableList(actionList);
        currentAction = 0;
        this.messages = new LinkedList<>();
        messages.add("Введите пин-код (по одной цифре)");
    }

    public String getPinCode() {
        return pinCode;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public Long getLockTime() {
        return lockTime;
    }

    public Queue<String> getMessages() {
        return messages;
    }

    public String takePin(String pinDigit) {

    }

    public String takeSelect(String selectDigit) {

    }


}
