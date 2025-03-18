package Bank;

import Accounts.Account;

public class BankCredentialsComparator implements Comparator<Bank> {
    // This comparator compares two Bank objects based on the owner's credentials.

    @Override
    public int compare(Bank b1, Bank b2) {
        String b1Pass = b1.getPasscode();
        String b2Pass = b2.getPasscode();
        String b1Name = b1.getName();
        String b2Name = b2.getName();

        if (!b1Pass.equals(b2Pass)) {
            return b1Pass.compareTo(b2Pass);
        }

        if (!b1Name.equals(b2Name)) {
            return b1Name.compareTo(b2Name);
        }

        return 0;
    }
}