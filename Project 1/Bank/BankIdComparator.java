package Bank;

public class BankIdComparator implements Comparator<Bank> {

    @Override
    public int compare(Bank b1, Bank b2) {
        return Integer.compare(b1.getID(), b2.getID());
    }
}