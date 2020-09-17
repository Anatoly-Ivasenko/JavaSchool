package org.jschool.terminal;

import java.util.*;

public class TerminalImpl implements Terminal {
    private static final String DIGIT_PATTERN = "[0-9]";
    private static final String SELECT_PATTERN = "[1-3]";
    private static final String PIN_CODE_WARN = "Неверный ввод. Введите одну цифру.\n";
    private static final String SELECT_ACTION_WARN = "Неверный ввод. Введите 1,2 или 3\n";
    private final TerminalServer server;
    private final PinValidator pinValidator;
    private final List<String> availableActions;
    private boolean isLogin;
    private boolean isLocked;
    private long lockTime = 0L;
    private int currentAction;
    private String pinCode;
    private int pinFailsCounter;

    private static class NotValidAmountOfMoneyException extends Exception {
        public NotValidAmountOfMoneyException(String message) {
            super(message);
        }
    }

    private static class AccountIsLockedException extends Exception {
        public AccountIsLockedException(String message) {
            super(message);
        }
    }

    public TerminalImpl(TerminalServer server, PinValidator pinValidator) {
        this.server = server;
        this.pinValidator = pinValidator;
        this.isLogin = false;
        this.isLocked = false;
        List<String> actionList = new ArrayList<>();
        actionList.add(0, "Авторизация");
        actionList.add(1, "Проверить состояние счета");
        actionList.add(2, "Снять деньги");
        actionList.add(3, "Положить деньги");
        this.availableActions = Collections.unmodifiableList(actionList);
        this.currentAction = 0;
        this.pinCode = "";
        this.pinFailsCounter = 0;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public List<String> getAvailableActions() {
        return availableActions;
    }

    public int getCurrentAction() {
        return currentAction;
    }

    public int getCurrentPinLength() {
        return pinCode.length();
    }

    public String login() {
        try {
            pinValidator.acceptPin(pinCode);
            isLogin = true;
            isLocked = false;
            lockTime = 0L;
            return "Вы авторизованы";
        } catch (NotValidPinCodException e) {
            pinFailsCounter++;
            if (pinFailsCounter == 3) {
                pinFailsCounter = 0;
                isLocked = true;
                lockTime = CurrentDateToSeconds(new Date().getTime());
                return "Не верный пин-код. Ваш аккаунт заблокирован на 10 секунд.";
            }
            return "Не верный пин-код.";
        } catch (Throwable e) {
            return "Неизвестная проблема, возможно нет связи с системой аутентификации";
        } finally {
            pinCode = "";
        }
    }

    @Override
    public String checkAccount() {
        try {
            return server.checkAccount();
        } catch (Throwable e) {
            return "Неизвестная проблема, возможно нет связи с сервером.";
        } finally {
            currentAction = 0;
        }

    }

    @Override
    public String cash(String input) {
        try {
            int amount = ParseAmountOfMoney(input);
            return server.acceptCash(amount);
        } catch (InputMismatchException e) {
            return "Введены некорректные данные. Попробуйте снова.";
        } catch (NotValidAmountOfMoneyException e) {
            return e.getMessage();
        } catch (NotEnoghtMoneyException e) {
            return "Не достаточно средств на счету.";
        } catch (Throwable e) {
            return "Неизвестная проблема, возможно нет связи с сервером.";
        } finally {
            currentAction = 0;
        }
    }

    @Override
    public String deposit(String input) {
        try {
            int amount = ParseAmountOfMoney(input);
            return server.acceptDeposit(amount);
        } catch (InputMismatchException e) {
            return "Введены некорректные данные. Попробуйте снова.";
        } catch (NotValidAmountOfMoneyException e) {
            return e.getMessage();
        } catch (Throwable e) {
            return "Неизвестная проблема, возможно нет связи с сервером.";
        } finally {
            currentAction = 0;
        }
    }

    public String takePin(String inputFromUI) {
        long currentTime = CurrentDateToSeconds(new Date().getTime());
        long delta = currentTime - lockTime;
        try {
            if (isLocked() && (delta < 10))
                throw new AccountIsLockedException("Аккаунт заблокирован. До снятия блокировки осталось " + (10 - delta) + " секунд");
            Scanner input = new Scanner(inputFromUI);
            pinCode = pinCode.concat(input.next(DIGIT_PATTERN));
            return "";
        } catch (InputMismatchException e) {
            return PIN_CODE_WARN;
        } catch (AccountIsLockedException e) {
            return e.getMessage();
        } finally {
            if (delta > 10) this.isLocked = false;
        }
    }

    public String selectAction(String inputFromUI) {
        try {
            Scanner input = new Scanner(inputFromUI);
            currentAction = Integer.parseInt(input.next(SELECT_PATTERN));
            return "";
        } catch (InputMismatchException e) {
            return SELECT_ACTION_WARN;
        }
    }

    private int ParseAmountOfMoney(String inputFromUI) throws NotValidAmountOfMoneyException {
        Scanner input = new Scanner(inputFromUI);
        int amount = input.nextInt();
        if ((amount % 100) == 0) return amount;
        else throw new NotValidAmountOfMoneyException("Не верная сумма. Сумма должна быть кратна 100");
    }

    private long CurrentDateToSeconds(long date) {
        return date / 1000;
    }



}


