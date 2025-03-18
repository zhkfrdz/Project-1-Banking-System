package Accounts.SavingsAccountModule;

import Accounts.Account;
import Accounts.IllegalAccountType;
import Bank.Bank;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public SavingsAccount(Bank bank,  String accountNumber, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double balance) {
        super(bank, accountNumber, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.balance = balance;
    }

    public String getAccountBalanceStatement() {
        String format = String.format("%.2f", balance);

        String account_statement = "Account Balance Statement:\n" +
                "Account Number: " + getAccountNumber() + "\n" +
                "Owner: " + getOwnerFullName() + "\n" +
                "Email: " + getOWNEREMAIL() + "\n" +
                "Balance: " + format + "\n";

        return account_statement;
    }

    private boolean hasEnoughBalance(double amount) {
        double newBalance = this.balance + amount;
        return newBalance >= 0.0;
    }

    private void insufficientBalance() {
        System.out.println("Insufficient balance for the transaction. Please check your account balance.");
    }

    private void adjustAccountBalance(double amount) {
        if (!hasEnoughBalance(amount)) {
            amount = 0.0;
            insufficientBalance();
            return;
        }
        this.balance += amount;
    }

    public String toString() {
        return getAccountBalanceStatement();
    }

    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        if (account instanceof SavingsAccount) {
            withdrawal(amount + bank.getPROCESSINGFEE());
            ((SavingsAccount) bank.getBankAccount(bank, account.getAccountNumber())).cashDeposit(amount);
            return true;
        } else {
            throw new IllegalAccountType("Attempted transfer from illegal account type.");
        }
    }

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

    @Override
    public boolean cashDeposit(double amount) {
        if (amount > getBank().getDEPOSITLIMIT()) {
            System.out.println("Deposit amount exceeds the deposit limit.");
            return false;
        }
        adjustAccountBalance(amount);
        return true;
    }

    @Override
    public boolean withdrawal(double amount) {
        if (hasEnoughBalance(amount)) {
            this.balance -= amount;
            return true;
        } else {
            insufficientBalance();
            return false;
        }
    }
}