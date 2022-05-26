package ru.job4j.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Класс реализует возможности сервиса по обраотке личных данных пользователей банка
 * @author Anton
 * @version 0.1
 */
public class BankService {

    /**
     * Обеспечивает хранение данных о пользователе в коллекции типа Map
     */
    private final Map<User, List<Account>> users = new HashMap<>();

    /**
     * Метод добавляет пользователя в систему
     * Метод putIfAbsent проверяет, что пользователь уникален
     * @param user
     */
    public void addUser(User user) {
            users.putIfAbsent(user, new ArrayList<Account>());
    }

    /**
     * Метод добаляет банковский счет для конкретного пользователя
     * @param passport - нужен для поиска пользователя в системе
     * @param account - счет, который нужно добавить пользователю
     * вызывается метод findByPassport - получаем доступ к счетам пользователя user
     * если user существует в системе и такого счета еще не было,
     * то добавляем счет для пользователя в коллекцию List
     */
    public void addAccount(String passport, Account account) {
        User user = findByPassport(passport);
        if (user != null) {
            List<Account> userBankList = users.get(user);
            if (!userBankList.contains(account)) {
                userBankList.add(account);
            }
        }
    }

    /**
     * Метод ищет пользователя User в системе по данным его паспорта
     * @param passport - данные поспорта User
     * @return - если пользователь ноайден то возвращает объект User, если нет - null
     */
    public User findByPassport(String passport) {
        User result = null;
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                result = user;
                break;
            }
        }
        return result;
    }

    /**
     * Метод осуществляет поиск счета account по реквизиту у пользователя User
     * @param passport - данные поспорта User необходимые для поиска его в системе
     * @param requisite - необходим для поиска конкретного счета пользователя
     * вызывается метод findByPassport - получаем доступ к счетам пользователя user
     * если user существует в системе то проходимся по его счетам циклом foreach
     * @return если у пользоввателя существует счет с таким реквизитом - возвращает этот счет
     * в другом случае - null
     */
    public Account findByRequisite(String passport, String requisite) {
        User user = findByPassport(passport);
        if (user != null) {
            List<Account> userBankList = users.get(user);
            for (Account slide : userBankList) {
                if (slide.getRequisite().equals(requisite)) {
                    return slide;
                }
            }
        }
        return null;
    }

    /**
     * Метод производит перевод между счетами двух пользователей User
     * @param srcPassport - данные паспорта пользователя user1 переводящего средства для поиска его в системае
     * @param srcRequisite - реквизит счета user 1, с которого средства будут списаны в случае успешной сделки
     * @param destPassport - данные паспорта получается средств user 2 для поиска его в системе
     * @param destRequisite - реквизит счета user2, на который будут переведены средства
     * @param amount - количество средств, которые нужно перевести на счет получателя со счета переводящего
     * По входящим параметрам паспорта и реквизита с помощью метода findByRequisite получаем доступ к счетам
     * пользователей, если счета участников сделаки существуют и на счету отправителя достаточно средств
     * то у отправителя средства уменьшаются на величину amount, а у получателя увеличиваются на величину amount
     * @return вовзращает true если сделка произведена, false если нет
     */
    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String destRequisite, double amount) {
        boolean rsl = false;
        Account srcAcc = findByRequisite(srcPassport, srcRequisite);
        Account destAcc = findByRequisite(destPassport, destRequisite);
            if (destAcc != null && srcAcc != null && amount <= srcAcc.getBalance()) {
                srcAcc.setBalance(srcAcc.getBalance() - amount);
                destAcc.setBalance(destAcc.getBalance() + amount);
                rsl = true;
            }
        return rsl;
    }
}