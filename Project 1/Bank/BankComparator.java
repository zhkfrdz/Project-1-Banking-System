package Bank;

public class BankComparator implements Comparator<Bank> {
    /**
     * Compares two Bank objects based on their ID, name, and passcode.
     *
     * @param  b1  the first Bank object to compare
     * @param  b2  the second Bank object to compare
     * @return     returns 0 if the two Bank objects are equal, -1 otherwise
     */
    @Override
    public int compare(Bank b1, Bank b2) {
        return (b1.getID() == b2.getID() &&
                b1.getName().equals(b2.getName()) &&
                b1.getPasscode().equals(b2.getPasscode())) ? 0 : -1;
    }
}
