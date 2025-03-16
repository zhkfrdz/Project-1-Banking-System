package Accounts.CreditAccountModule;

import Accounts.Account;
import Accounts.IllegalAccountType;
import Accounts.SavingsAccountModule.SavingsAccount;
import Bank.Bank;


/**
 * Represents a credit account that allows for payments and recompenses.
 */
public class CreditAccount extends Account implements Recompense, Payment {

    private double loan;

    /**
     * Constructs a CreditAccount with the specified details.
     *
     * @param bank         The bank associated with this account.
     * @param ACCOUNTNUMBER The account number.
     * @param OWNERFNAME   The first name of the account owner.
     * @param OWNERLNAME   The last name of the account owner.
     * @param OWNEREMAIL   The email of the account owner.
     * @param pin          The PIN for the account.
     */
    public CreditAccount(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {

        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.loan = 0.0;
    }

    /**
     * Retrieves the loan statement for the account.
     *
     * @return A string representation of the loan statement.
     */
    public String getLoanStatement() {

        String format = String.format("%.2f", this.loan);

        return  "Account Loan Statement:\n" +
                "Account Number: " + getACCOUNTNUMBER() + "\n" +
                "Owner: " + getOwnerFullName() + "\n" +
                "Email: " + getOWNEREMAIL() + "\n" +
                "Loan: " + format + "\n";
    }

    /**
     * Checks if the credit limit allows for the specified amount adjustment.
     *
     * @param amountAdjustment The amount to adjust the loan by.
     * @return True if the adjustment is within the credit limit, false otherwise.
     */
    private boolean canCredit(double amountAdjustment) {

        double newLoan = this.loan + amountAdjustment;
        double creditLimit = this.getBank().getCreditLimit();

        return newLoan <= creditLimit;
    }

    /**
     * Adjusts the loan amount by the specified adjustment.
     *
     * @param amountAdjustment The amount to adjust the loan by.
     */
    private void adjustLoanAmount(double amountAdjustment) {

        if (!canCredit(amountAdjustment)) {
            System.out.println("Cannot process: Exceeds credit limit");
            return;
        }

        this.loan += amountAdjustment;
    }

    /**
     * Pays a specified amount to another account.
     *
     * @param account The account to pay to.
     * @param amount  The amount to pay.
     * @return True if the payment was successful, false otherwise.
     * @throws IllegalAccountType If the account type is illegal for the payment.
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

    /**
     * Recompenses a specified amount to reduce the loan.
     *
     * @param amount The amount to recompense.
     * @return True if the recompense was successful, false otherwise.
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
     * Returns a string representation of the credit account details.
     *
     * @return A string containing the account number, owner's name, email, and loan amount.
     */
    @Override
    public String toString() {

        String creditAcc = "";
        creditAcc += "Account Number: " + getACCOUNTNUMBER() + "\n";
        creditAcc += "Name: " + getOWNERFNAME() + " " + getOWNERLNAME() + "\n";
        creditAcc += "Email: " + getOWNEREMAIL() + "\n";
        creditAcc += "Credit: " + this.loan + "\n";

        return creditAcc;
    }

}