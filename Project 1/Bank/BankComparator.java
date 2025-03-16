package Bank;

public class BankComparator implements Comparator<Bank> {
    // This comparator compares two Bank objects based on their ID, name, and passcode.

    /**
     * Compares two Bank objects based on their ID, name, and passcode.
     *

     *
     * @param  b1  the first Bank object to compare
     * @param  b2  the second Bank object to compare
     * @return     0 if the Bank objects are equal, -1 otherwise
     */
    @Override
    public int compare(Bank b1, Bank b2) {
        if (b1.getID() == b2.getID() && b1.getName().equals(b2.getName()) && b1.getPasscode().equals(b2.getPasscode())) {
            return 0;
        }
        return -1; // Banks are not equal

    }
}