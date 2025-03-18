package Accounts.CreditAccountModule;
import Accounts.Account;
import Accounts.IllegalAccountType;
import Bank.Bank;
import Accounts.SavingsAccountModule.SavingsAccount;
public class CreditAccount extends Account implements Payment, Recompense {
    private double loan;

    public double getLoan() {
        return loan;
    }

    public void setLoan(double loan) {
        this.loan = loan;
    }

    public CreditAccount(Bank bank,  String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double loan) {
        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.loan = loan;
    }

    public String getLoanStatement() {
        String loan_statement = "Loan Statement:\n" +
                "Owner: " + getOwnerFullName() + "\n" +
                "Account Number: " + getAccountNumber() + "\n" +
                "Loan Amount: " + loan + "\n";
        return loan_statement;
    }

    /**
     * Checks if this credit account can perform a credit transaction without exceeding the credit limit.
     *
     * @param amountAdjustment The amount to adjust for the transaction.
     * @return True if the credit transaction can proceed without exceeding the credit limit, false otherwise.
     *
     * This method calculates the new loan amount after adjusting it by the provided amount for the transaction.
     * It retrieves the credit limit set by the associated bank and compares the new loan amount with the credit limit.
     * If the new loan amount is less than or equal to the credit limit, it returns true indicating that the credit transaction
     * can proceed without exceeding the credit limit. Otherwise, it returns false.
     */
    private boolean canCredit(double amountAdjustment) {
        double newLoan = this.loan + amountAdjustment;
        double creditLimit = this.getBank().getCREDITLIMIT();

        return newLoan <= creditLimit;
    }

    /**
     * Adjusts the loan amount by the specified amount if it doesn't exceed the credit limit.
     *
     * @param  amountAdjustment    the amount to adjust the loan by
     */
    private void adjustLoanAmount(double amountAdjustment) {
        if (!canCredit(amountAdjustment)) {
            System.out.println("Cannot process: Exceeds credit limit");
            return;
        }

        this.loan += amountAdjustment;
    }

    /**
     * Returns a string representation of the CreditAccount object, including its account number, owner's full name,
     * and the loan amount.
     *
     * @return a formatted string representing the CreditAccount object
     */
    @Override
    public String toString() {
        String format = String.format("%.2f", loan);

        String account_statement = "Account Balance Statement:\n" +
                "Account Number: " + getAccountNumber() + "\n" +
                "Owner: " + getOwnerFullName() + "\n" +
                "Email: " + getOWNEREMAIL() + "\n" +
                "Balance: " + format + "\n";

        return account_statement;
    }

    /**
     * A method to recompense a certain amount of money.
     *
     * @param  amount  the amount of money to be recompensed
     * @return         true if the recompense is successful, false if not
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
     * Pay the specified amount to the given account.
     *
     * @param  account   the account to pay from
     * @param  amount    the amount to pay
     * @return           true if the payment was successful, false otherwise
     * @throws IllegalAccountType if the account is not a CreditAccount
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
            adjustLoanAmount(amount + getBank().getPROCESSINGFEE());
            ((SavingsAccount) account).cashDeposit(amount);
            return true;
        } else {
            adjustLoanAmount(amount);
            ((SavingsAccount) account).cashDeposit(amount);
            return true;
        }
    }
}