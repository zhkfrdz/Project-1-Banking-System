package Bank;

public class BankIdComparator implements Comparator<Bank> {
    // This comparator compares two Bank objects based on their IDs.

    /**
     * Compares two Bank objects based on their IDs.
     *

     *
     * @param  b1  the first Bank object to compare
     * @param  b2  the second Bank object to compare
     * @return     the result of the comparison
     */
    @Override
    public int compare(Bank b1, Bank b2) {
        return Integer.compare(b1.getID(), b2.getID()); // b1 has a smaller ID than b2
    }
}