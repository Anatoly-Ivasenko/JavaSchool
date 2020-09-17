package org.jschool.terminal;

import java.util.LinkedList;
import java.util.Scanner;

public class TerminalUI {
    private static final String WELCOME = "\nДобро пожаловать!";
    private static final String PIN_INVITE = "\nВведите Ваш пин-код";
    private static final String PIN_INTRO = "Ваш пин-код: ";
    private static final String INPUT_INVITE = "\nВведите ";
    private static final String DIGIT_OF_PIN_TEXT = " цифру пин-кода:";
    private static final String PIN_MASK = "*";
    private static final String SELECT_INVITE = "\nВыберете действие";
    private static final String MONEY_VALUE_INVITE = "\nВведите сумму: ";
    private final TerminalImpl terminal;
    private final LinkedList<String> messages;

    public TerminalUI(TerminalServer server, PinValidator pinValidator) {
        this.terminal = new TerminalImpl(server, pinValidator);
        this.messages = new LinkedList<>();
        messages.add(WELCOME);
    }

    public static void main(String[] args) {
        TerminalUI terminalUI = new TerminalUI(server, pinValidator);
        while (true) {
            terminalUI.getMessages();
            while (terminalUI.messages.peek() != null) System.out.print(terminalUI.messages.poll());
            Scanner input = new Scanner(System.in);
            terminalUI.takeInput(input.next());
            while (terminalUI.messages.peek() != null) System.out.print(terminalUI.messages.poll());
        }
    }

    private void getMessages() {
        switch (terminal.getCurrentAction()) {
            case (0): {
                if (!terminal.isLogin()) {
                    int currentPinCodeLength = terminal.getCurrentPinLength();
                    if (currentPinCodeLength == 0) messages.add(PIN_INVITE);
                    if (currentPinCodeLength > 0) {
                        String pinAsterisk = PIN_INTRO;
                        for (int i = 0; i < currentPinCodeLength; i++) pinAsterisk = pinAsterisk.concat(PIN_MASK);
                        messages.add(pinAsterisk);
                    }
                    messages.add(INPUT_INVITE + (currentPinCodeLength + 1) + DIGIT_OF_PIN_TEXT);
                } else {
                    messages.add(SELECT_INVITE);
                    int i = 1;
                    while (terminal.getAvailableActions().get(i) != null) {
                        messages.add(i + ". " + terminal.getAvailableActions().get(i));
                    }
                }
            }

            case (1): {
                messages.add(terminal.checkAccount());
            }

            case (2-3): {
                messages.add(MONEY_VALUE_INVITE);
            }
        }
    }

    private void takeInput(String inputFromUI) {
        switch (terminal.getCurrentAction()) {
            case (0): {
                if (!terminal.isLogin()) {
                    messages.add(terminal.takePin(inputFromUI));
                    if (terminal.getCurrentPinLength() == 4) {
                       messages.add(terminal.login());
                    }
                }
                else {
                    messages.add(terminal.selectAction(inputFromUI));
                }
            }

            case (2): {
                messages.add(terminal.cash(inputFromUI));
            }

            case (3): {
                messages.add(terminal.deposit(inputFromUI));
            }
        }
    }
}
