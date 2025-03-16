package Accounts;

import Bank.Bank;

import java.util.ArrayList;

/**
 * Represents a bank account with attributes and methods for managing transactions.
 */
public abstract class Account {

    private final Bank BANK; // The bank associated with the account
    private final String ACCOUNTNUMBER; // Unique account number
    private final String OWNERFNAME, OWNERLNAME, OWNEREMAIL; // Owner's details
    private String pin; // Account PIN
    private final ArrayList<Transaction> TRANSACTIONS; // List of transactions associated with the account

    /**
     * Constructor to initialize an account with the owner's details and associated bank.
     *
     * @param bank The bank associated with the account
     * @param ACCOUNTNUMBER The unique account number
     * @param OWNERFNAME The owner's first name
     * @param OWNERLNAME The owner's last name
     * @param OWNEREMAIL The owner's email address
     * @param pin The account PIN
     */
    public Account(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {

        this.BANK = bank;
        this.ACCOUNTNUMBER = ACCOUNTNUMBER;
        this.OWNERFNAME = OWNERFNAME;
        this.OWNERLNAME = OWNERLNAME;
        this.OWNEREMAIL = OWNEREMAIL;
        this.pin = pin;
        this.TRANSACTIONS = new ArrayList<>();
    }

    /**
     * Gets the bank associated with the account.
     *
     * @return The bank associated with the account
     */
    public Bank getBank() {

        return this.BANK;
    }

    /**
     * Gets the unique account number.
     *
     * @return The account number
     */
    public String getACCOUNTNUMBER() {

        return this.ACCOUNTNUMBER;
    }

    /**
     * Gets the owner's first name.
     *
     * @return The owner's first name
     */
    public String getOWNERFNAME() {

        return this.OWNERFNAME;
    }

    /**
     * Gets the owner's last name.
     *
     * @return The owner's last name
     */
    public String getOWNERLNAME() {

        return this.OWNERLNAME;
    }

    /**
     * Gets the owner's email address.
     *
     * @return The owner's email address
     */
    public String getOWNEREMAIL() {

        return this.OWNEREMAIL;
    }

    /**
     * Gets the account PIN.
     *
     * @return The account PIN
     */
    public String getPin() {

        return this.pin;
    }

    /**
     * Gets the list of transactions associated with the account.
     *
     * @return The list of transactions
     */
    public ArrayList<Transaction> getTRANSACTIONS() {

        return this.TRANSACTIONS;
    }

    /**
     * Gets the full name of the account owner.
     *
     * @return The full name of the owner
     */
    public String getOwnerFullName() {

        return this.getOWNERFNAME() + " " + this.getOWNERLNAME();
    }

    /**
     * Adds a new transaction to the account's transaction history.
     *
     * @param accountNum The account number associated with the transaction
     * @param type The type of transaction (e.g., deposit, withdrawal)
     * @param description A description of the transaction
     */
    public void addNewTransaction(String accountNum, Transaction.Transactions type, String description) {

        Transaction transaction = new Transaction(accountNum, type, description);
        getTRANSACTIONS().add(transaction);
        System.out.println("New Transaction Added to this Account.");
    }

    /**
     * Retrieves information about the account's transactions.
     *
     * @return A string containing transaction details
     */
    public String getTransactionsInfo() {

        StringBuilder result = new StringBuilder("Transactions: \n");
        for(Transaction transaction : getTRANSACTIONS()) {
            result.append(transaction.description).append("\n");
        }
        return result.toString();
    }

    /**
     * Returns a string representation of the account's information.
     *
     * @return A string containing account details
     */
    public String toString() {

        String account = "";
        account += "Account Number: " + getACCOUNTNUMBER() + "\n";
        account += "Name: " + getOWNERFNAME() + " " + getOWNERLNAME() + "\n";
        account += "Email: " + getOWNEREMAIL() + "\n";

        return account;
    }

}