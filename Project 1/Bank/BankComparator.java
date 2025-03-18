package Bank;

public class BankComparator implements Comparator<Bank> {

    @Override
    public int compare(Bank b1, Bank b2) {
        return (b1.getID() == b2.getID() &&
                b1.getName().equals(b2.getName()) &&
                b1.getPasscode().equals(b2.getPasscode())) ? 0 : -1;
    }
}
