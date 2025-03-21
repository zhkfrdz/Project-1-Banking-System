package Bank;

public class BankIdComparator implements Comparator<Bank> {

    @Override
    /**
     * Compares two Bank objects based on their IDs.
     * @param b1 the first bank to compare
     * @param b2 the second bank to compare
     * @return a negative integer, zero, or a positive integer as the first bank's ID is less than, equal to, or greater than the second bank's ID
     */
    public int compare(Bank b1, Bank b2) {

        return Integer.compare(b1.getID(), b2.getID());
    }
}