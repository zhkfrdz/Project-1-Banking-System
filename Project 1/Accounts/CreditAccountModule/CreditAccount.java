package Accounts.CreditAccountModule;

import Accounts.Account;
import Bank.Bank;

public class CreditAccount extends Account implements ICreditAccount {
    private double creditLimit;
    private double balance;

    /**
     * Constructor for initializing a credit account.
     * @param bank the bank associated with the account
     * @param ACCOUNTNUMBER the account number
     * @param OWNERFNAME the owner's first name
     * @param OWNERLNAME the owner's last name
     * @param OWNEREMAIL the owner's email
     * @param pin the account PIN
     * @param creditLimit the credit limit for the account
     */
    public CreditAccount(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double creditLimit) {
        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.creditLimit = creditLimit;
        this.balance = 0.0; 
    }

    @Override
    public boolean makePurchase(double amount) {
        if (balance + amount <= creditLimit) {
            balance += amount;
            addNewTransaction(getAccountNumber(), Transaction.Transactions.PURCHASE, "Purchase of $" + amount);
            return true;
        } else {
            System.out.println("Purchase exceeds credit limit.");
            return false;
        }
    }

    @Override
    public void makePayment(double amount) {
        balance -= amount;
        if (balance < 0) {
            balance = 0; 
        }
        addNewTransaction(getAccountNumber(), Transaction.Transactions.PAYMENT, "Payment of $" + amount);
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public double getCreditLimit() {
        return creditLimit;
    }

    @Override
    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }
}