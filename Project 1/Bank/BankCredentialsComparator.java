package Bank;

import Accounts.Account;

public class BankCredentialsComparator implements Comparator<Bank> {
    // This comparator compares two Bank objects based on the owner's credentials.

    /**
     * Compares two Bank objects based on the owner's first name, last name, and email.
     *

     *
     * @param  b1  the first Bank object to compare
     * @param  b2  the second Bank object to compare
     * @return     -1 if b1 is less than b2, 1 if b1 is greater than b2, 0 if they are equal
     */
    @Override
    public int compare(Bank b1, Bank b2) {
        for (Account account1 : b1.getBANKACCOUNTS()) {
            for (Account account2 : b2.getBANKACCOUNTS()) {
                if ((account1.getOWNERFNAME().compareTo(account2.getOWNERFNAME()) < 0)
                        && (account1.getOWNERLNAME().compareTo(account2.getOWNERLNAME()) < 0)
                        && (account1.getOWNEREMAIL().compareTo(account2.getOWNEREMAIL()) < 0)) {
                    return -1; // b1 is less than b2 based on credentials

                } else if ((account1.getOWNERFNAME().compareTo(account2.getOWNERFNAME()) > 0)
                        && (account1.getOWNERLNAME().compareTo(account2.getOWNERLNAME()) > 0)
                        && (account1.getOWNEREMAIL().compareTo(account2.getOWNEREMAIL()) > 0)) {
                    return 1;
                }
            }
        }
        return 0;
    }
}