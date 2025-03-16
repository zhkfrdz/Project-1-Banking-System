package Accounts.SavingsAccountModule;

import Bank.Bank;
import Accounts.Account;
import Accounts.IllegalAccountType;

/**
 * Represents a savings account that allows for deposits, withdrawals, and fund transfers.
 */
public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {

    private double balance;

    /**
     * Constructs a SavingsAccount with the specified details.
     *
     * @param bank         The bank associated with this account.
     * @param ACCOUNTNUMBER The account number.
     * @param OWNERFNAME   The first name of the account owner.
     * @param OWNERLNAME   The last name of the account owner.
     * @param OWNEREMAIL   The email of the account owner.
     * @param pin          The PIN for the account.
     * @param balance      The initial balance of the account.
     */
    public SavingsAccount(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double balance) {

        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.balance = balance;
    }

    /**
     * Retrieves the current balance of the account.
     *
     * @return The current balance.
     */
    public double getBalance() {

        return this.balance;
    }

    /**
     * Generates a statement of the account balance.
     *
     * @return A string representation of the account balance statement.
     */
    public String getAccountBalanceStatement() {


        return "Account Balance Statement:\n" +
                "Account Number: " + getACCOUNTNUMBER() + "\n" +
                "Owner: " + getOwnerFullName() + "\n" +
                "Email: " + getOWNEREMAIL() + "\n" +
                "Balance: " + getBalance() + "\n";
    }

    /**
     * Checks if there is enough balance for a transaction.
     *
     * @param amount The amount to check.
     * @return True if there is enough balance, false otherwise.
     */
    private boolean hasEnoughBalance(double amount) {

        double newBalance = this.balance + amount;
        return newBalance >= 0.0;
    }

    /**
     * Displays a message indicating insufficient balance for a transaction.
     */
    private void insufficientBalance() {

        System.out.println("Insufficient balance for the transaction. Please check your account balance.");

    }

    private void adjustAccountBalance(double amount) {
        if (!hasEnoughBalance(amount)) {
            insufficientBalance();
            return;
        }

        this.balance += amount;
    }

    /**
     * Withdraws a specified amount from the account.
     *
     * @param amount The amount to withdraw.
     * @return True if the withdrawal was successful, false otherwise.
     */
    @Override
    public boolean withdrawal(double amount) {

        if (amount > getBank().getWithdrawLimit()) {
            System.out.println("Withdrawal amount exceeds the withdraw limit.");
            return false;
        }

        adjustAccountBalance(-amount);
        return true;
    }

    /**
     * Deposits a specified amount into the account.
     *
     * @param amount The amount to deposit.
     * @return True if the deposit was successful, false otherwise.
     */
    @Override
    public boolean cashDeposit(double amount) {

        if (amount > getBank().getDepositLimit()) {
            System.out.println("Deposit amount exceeds the deposit limit.");
            return false;
        }

        adjustAccountBalance(amount);
        return true;
    }

    /**
     * Transfers a specified amount to another account.
     *
     * @param bank    The bank associated with the target account.
     * @param account The target account to transfer to.
     * @param amount  The amount to transfer.
     * @return True if the transfer was successful, false otherwise.
     * @throws IllegalAccountType If the account type is illegal for the transfer.
     */
    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {

        if (account instanceof SavingsAccount) {
            withdrawal(amount + bank.getProcessingFee());
            ((SavingsAccount) bank.getBankAccount(bank, account.getACCOUNTNUMBER())).cashDeposit(amount);
            return true;
        } else {
            throw new IllegalAccountType("Attempted transfer from illegal account type.");
        }
    }

    /**
     * Transfers a specified amount to another savings account.
     *
     * @param account The target savings account to transfer to.
     * @param amount  The amount to transfer.
     * @return True if the transfer was successful, false otherwise.
     * @throws IllegalAccountType If the account type is illegal for the transfer.
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
     * Returns a string representation of the savings account details.
     *
     * @return A string containing the account number, owner's name, email, and balance.
     */
    @Override
    public String toString() {

        String savingsAcc = "";
        savingsAcc += "Account Number: " + getACCOUNTNUMBER() + "\n";
        savingsAcc += "Name: " + getOWNERFNAME() + " " + getOWNERLNAME() + "\n";
        savingsAcc += "Email: " + getOWNEREMAIL() + "\n";
        savingsAcc += "Balance: " + this.balance + "\n";

        return savingsAcc;
    }
}