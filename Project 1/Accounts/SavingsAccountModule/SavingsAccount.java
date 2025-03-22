package Accounts.SavingsAccountModule;

import Accounts.Account;
import Accounts.IllegalAccountType;
import Bank.Bank;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {
    private double balance;

    /**
     * Returns the current balance of the account.
     * @return the balance
     */
    public double getBalance() {

        return balance;
    }

    /**
     * Sets the balance of the account.
     * @param balance the balance to set
     */
    public void setBalance(double balance) {

        this.balance = balance;
    }

    /**
     * Constructor for initializing a SavingsAccount object.
     * @param bank the bank associated with the account
     * @param accountNumber the account number
     * @param OWNERFNAME the owner's first name
     * @param OWNERLNAME the owner's last name
     * @param OWNEREMAIL the owner's email
     * @param pin the account PIN
     * @param balance the initial balance
     */
    public SavingsAccount(Bank bank,  String accountNumber, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double balance) {

        super(bank, accountNumber, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.balance = balance;
    }

    /**
     * Generates a statement of the account balance.
     * @return a string representation of the account balance statement
     */
    public String getAccountBalanceStatement() {

        String format = String.format("%.2f", balance);

        String account_statement = "Account Balance Statement:\n" +
                "Account Number: " + getAccountNumber() + "\n" +
                "Owner: " + getOwnerFullName() + "\n" +
                "Email: " + getOWNEREMAIL() + "\n" +
                "Balance: " + format + "\n";

        return account_statement;
    }

    /**
     * Checks if the account has enough balance for a transaction.
     * @param amount the amount to check
     * @return true if there is enough balance, false otherwise
     */
    private boolean hasEnoughBalance(double amount) {

        double newBalance = this.balance + amount;
        return newBalance >= 0.0;
    }

    /**
     * Displays a message when the balance is insufficient for a transaction.
     */
    private void insufficientBalance() {

        System.out.println("Insufficient balance for the transaction. Please check your account balance.");
    }

    /**
     * Adjusts the account balance based on the provided amount.
     * @param amount the amount to adjust the balance by
     */
    private void adjustAccountBalance(double amount) {

        if (!hasEnoughBalance(amount)) {
            amount = 0.0;
            insufficientBalance();
            return;
        }
        this.balance += amount;
    }

    /**
     * Returns a string representation of the account balance statement.
     * @return a string representation of the account balance statement
     */
    public String toString() {

        return getAccountBalanceStatement();
    }

    /**
     * Transfers an amount to another account in a different bank.
     * @param bank the bank to transfer to
     * @param account the account to transfer to
     * @param amount the amount to transfer
     * @return true if the transfer was successful, false otherwise
     * @throws IllegalAccountType if the account type is invalid
     */
    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {

        if (account instanceof SavingsAccount) {
            withdrawal(amount + bank.getProcessingFee());
            ((SavingsAccount) bank.getBankAccount(bank, account.getAccountNumber())).cashDeposit(amount);
            return true;
        } else {
            throw new IllegalAccountType("Attempted transfer from illegal account type.");
        }
    }

    /**
     * Transfers an amount to another account within the same bank.
     * @param account the account to transfer to
     * @param amount the amount to transfer
     * @return true if the transfer was successful, false otherwise
     * @throws IllegalAccountType if the account type is invalid
     */
    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {

        if (account instanceof SavingsAccount) {
            withdrawal(amount);
            ((SavingsAccount) account).cashDeposit(amount);
            return true;
        } else {
            throw new IllegalAccountType("Attempted transfer from illegal account type.");
        }
    }

    /**
     * Deposits an amount into the account.
     * @param amount the amount to deposit
     * @return true if the deposit was successful, false otherwise
     */
    @Override
    public boolean cashDeposit(double amount) {

        if (amount > getBank().getDEPOSITLIMIT()) {
            System.out.println("Deposit amount exceeds the deposit limit.");
            return false;
        }
        adjustAccountBalance(amount);
        return true;
    }

    /**
     * Withdraws an amount from the account, enforcing a withdrawal limit.

     * @param amount the amount to withdraw
     * @return true if the withdrawal was successful, false otherwise
     */
    @Override
    public boolean withdrawal(double amount) {

        if (amount > getBank().getWITHDRAWLIMIT()) {
            System.out.println("Withdrawal amount exceeds the withdraw limit.");
            return false;
        }
        if (hasEnoughBalance(amount) ) {

            this.balance -= amount;
            return true;
        } else {
            insufficientBalance();
            return false;
        }
    }

    public double getAccountBalance() {
        return this.balance;
    }
}
