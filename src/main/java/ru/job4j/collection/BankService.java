package ru.job4j.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankService {
    private final Map<User, List<AccountBank>> users = new HashMap<>();

    public void addUser(User user) {
            users.putIfAbsent(user, new ArrayList<AccountBank>());
    }

    public void addAccount(String passport, AccountBank account) {
        User user = findByPassport(passport);
        if (user != null) {
            List<AccountBank> userBankList = users.get(user);
            if (!users.get(user).contains(account)) {
                userBankList.add(account);
                users.put(user, userBankList);
            }
        }
    }

    public User findByPassport(String passport) {
        User userfbp = null;
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                userfbp = user;
                break;
            }
        }
        return userfbp;
    }

    public AccountBank findByRequisite(String passport, String requisite) {
        User user = findByPassport(passport);
        if (user != null) {
            List<AccountBank> userBankList = users.get(user);
            for (AccountBank slide : userBankList) {
                if (slide.getRequisite().equals(requisite)) {
                    return slide;
                }
            }
        }
        return null;
    }

    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String destRequisite, double amount) {
        boolean rsl = false;
        AccountBank srcAcc = findByRequisite(srcPassport, srcRequisite);
        AccountBank destAcc = findByRequisite(destPassport, destRequisite);
        if (destAcc != null && srcAcc != null) {
            if (amount <= srcAcc.getBalance()) {
                srcAcc.setBalance(srcAcc.getBalance() - amount);
                destAcc.setBalance(destAcc.getBalance() + amount);
                rsl = true;
            }
        }
        return rsl;
    }
}
