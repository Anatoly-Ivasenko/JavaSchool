package org.jschool.terminal;

import java.util.Scanner;

public class TerminalUI {

    public static void main(String[] args) {
        TerminalImpl terminal = new TerminalImpl(server, pinValidator);
        Scanner input = new Scanner(System.in);
        while (true) {
            while (terminal.getMessages().peek() != null) System.out.println(terminal.getMessages().poll());
            System.out.println(terminal.takeInput(input.next()));
        }

//        while (true) {
//            TerminalUI.login(terminal);
//        }
//    }
//
//    static void login(TerminalImpl terminal) {
//        Scanner input = new Scanner(System.in);
//        while (!terminal.isLogin()) {
//            System.out.println("Введите " + (terminal.getPinCode().length() + 1) + "цифру пин-кода");
//            System.out.println(terminal.takePin(input.next()));
//        }
//    }
//
//    static void selectOption(TerminalImpl terminal) {
//        Scanner input = new Scanner(System.in);
//        System.out.println("Выберите желаемое действие:");
//        System.out.println("1. Проверить состояние счета.");
//        System.out.println("2. Положить деньги.");
//        System.out.println("3. Снять деньги.");
//        System.out.println(terminal.takeSelect(input.next()));
//    }


}

