package Accounts;

import java.util.ArrayList;
import Bank.Bank;

public abstract class Account {
    private Bank bank;
    private final String ACCOUNTNUMBER;
    private final String OWNERFNAME, OWNERLNAME, OWNEREMAIL;
    private String pin;
    private final ArrayList<Transaction> TRANSACTIONS;

    /**
     * Returns the bank associated with the account.
     * @return the bank
     */
    public Bank getBank() {

        return bank;
    }
    /**
     * Sets the bank for the account.
     * @param bank the bank to set
     */
    public void setBank(Bank bank) {

        this.bank = bank;
    }
    /**
     * Returns the PIN for the account.
     * @return the PIN
     */
    public String getPin() {

        return pin;
    }
    /**
     * Sets the PIN for the account.
     * @param pin the PIN to set
     */
    public void setPin(String pin) {

        this.pin = pin;
    }
    /**
     * Returns the account number.
     * @return the account number
     */
    public String getAccountNumber() {

        return ACCOUNTNUMBER;
    }
    /**
     * Returns the owner's first name.
     * @return the owner's first name
     */
    public String getOWNERFNAME() {

        return OWNERFNAME;
    }
    /**
     * Returns the owner's last name.
     * @return the owner's last name
     */
    public String getOWNERLNAME() {

        return OWNERLNAME;
    }

    /**
     * Returns the owner's email.
     * @return the owner's email
     */
    public String getOWNEREMAIL() {

        return OWNEREMAIL;
    }
    /**
     * Returns the list of transactions associated with the account.
     * @return the list of transactions
     */
    public ArrayList<Transaction> getTRANSACTIONS() {

        return new ArrayList<> (TRANSACTIONS);
    }

    /**
     * Constructor for initializing an account.
     * @param bank the bank associated with the account
     * @param ACCOUNTNUMBER the account number
     * @param OWNERFNAME the owner's first name
     * @param OWNERLNAME the owner's last name
     * @param OWNEREMAIL the owner's email
     * @param pin the account PIN
     */
    public Account(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        if (bank == null || ACCOUNTNUMBER == null || OWNERFNAME == null || OWNERLNAME == null || OWNEREMAIL == null || pin == null) {
            throw new IllegalArgumentException("none of the parameters can be null");
        }
        this.bank = bank;
        this.ACCOUNTNUMBER = ACCOUNTNUMBER;
        this.OWNERFNAME = OWNERFNAME;
        this.OWNERLNAME = OWNERLNAME;
        this.OWNEREMAIL = OWNEREMAIL;
        this.pin = pin;
        this.TRANSACTIONS = new ArrayList<>();
    }

    /**
     * Returns the full name of the account owner.
     * @return the full name of the owner
     */
    public String getOwnerFullName() {

        return this.OWNERFNAME + " " + this.OWNERLNAME;
    }

    /**
     * Adds a new transaction to the account.
     * @param accountNum the account number associated with the transaction
     * @param type the type of transaction
     * @param description a description of the transaction
     */
    public void addNewTransaction(String accountNum, Transaction.Transactions type, String description) {

        Transaction transaction = new Transaction(accountNum, type, description);
        TRANSACTIONS.add(transaction);
        System.out.println("A successful " + type + ".");
    }

    /**
     * Returns a string representation of the account's transaction history.
     * @return a string containing transaction information
     */
    public String getTransactionsInfo() {

        StringBuilder transactionsInfo = new StringBuilder("Transactions for the Account Number: " + ACCOUNTNUMBER + "\n");

        int i = 0;
        while (i < TRANSACTIONS.size()) {
            Transaction transaction = TRANSACTIONS.get(i);
            transactionsInfo.append("Transaction Type: ").append(transaction.transactionType).append("\n");
            transactionsInfo.append("Description: ").append(transaction.description).append("\n");
            i++;
        }
        return transactionsInfo.toString();
    }

    /**
     * Returns a string representation of the account information.
     * @return a string containing account details
     */
    public String toString() {

        //Initialize the string with a header
        StringBuilder accountInfo = new StringBuilder("Account Information:\n");

        //Append bank information or indicate if not available
        accountInfo.append(String.format("Bank:\n%s", bank != null ? bank.toString() : "No bank information available")).append("\n");
        //Append account details
        accountInfo.append(String.format("Account Number: %s\n", ACCOUNTNUMBER));
        accountInfo.append(String.format("Owner: %s\n", getOwnerFullName()));
        accountInfo.append(String.format("Owner Email: %s\n", OWNEREMAIL));
        accountInfo.append(String.format("PIN: %s\n", pin));

        //Append transaction details or indicate if no transactions available
        if (!TRANSACTIONS.isEmpty()) {
            accountInfo.append("Transactions:\n");
            for (Transaction transaction : TRANSACTIONS) {
                accountInfo.append(String.format("- %s\n", transaction != null ? transaction.toString() : "Invalid Transaction"));
            }
        } else {
            accountInfo.append("No transactions available.\n");
        }
        // Return the final string representation
        return accountInfo.toString();
    }


}
