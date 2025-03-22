package Accounts.CreditAccountModule;

import Accounts.Account;
import Accounts.IllegalAccountType;
import Bank.Bank;
import Accounts.SavingsAccountModule.SavingsAccount;

public class CreditAccount extends Account implements Payment, Recompense {
    private double loan;

    /**
     * Returns the current loan amount.
     * @return the loan amount
     */
    public double getLoan() {

        return loan;
    }

    /**
     * Sets the loan amount.
     * @param loan the loan amount to set
     */
    public void setLoan(double loan) {

        this.loan = loan;
    }

    /**
     * Constructor for initializing a CreditAccount object.
     * @param bank the bank associated with the account
     * @param ACCOUNTNUMBER the account number
     * @param OWNERFNAME the owner's first name
     * @param OWNERLNAME the owner's last name
     * @param OWNEREMAIL the owner's email
     * @param pin the account PIN
     * @param loan the initial loan amount
     */
    public CreditAccount(Bank bank,  String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double loan) {

        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.loan = loan;
    }

    /**
     * Generates a loan statement for the account.
     * @return a string representation of the loan statement
     */
    public String getLoanStatement() {

        return "Loan Statement:\n" +
                "Owner: " + getOwnerFullName() + "\n" +
                "Account Number: " + getAccountNumber() + "\n" +
                "Loan Amount: " + loan + "\n";
    }

    /**
     * Checks if the new loan amount after adjustment is within the credit limit.
     * @param amountAdjustment the amount to adjust the loan by
     * @return true if the new loan amount is within the credit limit, false otherwise
     */
    private boolean canCredit(double amountAdjustment) {

        double newLoan = this.loan + amountAdjustment;
        double creditLimit = this.getBank().getCREDITLIMIT();

        return newLoan <= creditLimit;
    }

    /**
     * Adjusts the loan amount based on the provided adjustment.
     * @param amountAdjustment the amount to adjust the loan by
     */
    private void adjustLoanAmount(double amountAdjustment) {

        if (!canCredit(amountAdjustment)) {
            System.out.println("Cannot process payment: Credit exceeds limit. Please reduce the amount or check your credit limit.");
            return;
        }

        this.loan += amountAdjustment;
    }

    /**
     * Returns a string representation of the account balance statement.
     * @return a string representation of the account balance statement
     */
    @Override
    public String toString() {

        String format = String.format("%.2f", loan);

        return "Account Balance Statement:\n" +
                "Account Number: " + getAccountNumber() + "\n" +
                "Owner: " + getOwnerFullName() + "\n" +
                "Email: " + getOWNEREMAIL() + "\n" +
                "Balance: " + format + "\n";
    }

    /**
     * Recompenses a specified amount against the loan.
     * @param amount the amount to recompense
     * @return true if recompense was successful, false otherwise
     */
    @Override
    public boolean recompense(double amount) {

        if (amount <= 0 || amount > this.loan) {
            return false;
        }

        adjustLoanAmount(-amount);
        return true;
    }

    /**
     * Processes a payment to another account.
     * @param account the account to pay to
     * @param amount the amount to pay
     * @return true if the payment was successful, false otherwise
     * @throws IllegalAccountType if trying to pay to another CreditAccount
     */
    @Override
    public boolean pay(Account account, double amount) throws IllegalAccountType {

        if (account instanceof CreditAccount) {
            throw new IllegalAccountType("Credit Accounts cannot pay to other Credit Accounts.");
        }

        if (amount <= 0) {
            return false;
        }

        if (getBank() != account.getBank()) {
            adjustLoanAmount(amount + getBank().getProcessingFee());
        } else {
            adjustLoanAmount(amount);
        }
        ((SavingsAccount) account).cashDeposit(amount);
        return true;
    }
}
