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

    private boolean canCredit(double amountAdjustment) {
        double newLoan = this.loan + amountAdjustment;
        double creditLimit = this.getBank().getCREDITLIMIT();

        return newLoan <= creditLimit;
    }

    private void adjustLoanAmount(double amountAdjustment) {
        if (!canCredit(amountAdjustment)) {
            System.out.println("Cannot process: Exceeds credit limit");
            return;
        }

        this.loan += amountAdjustment;
    }

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

    @Override
    public boolean recompense(double amount) {
        if (amount <= 0 || amount > this.loan) {
            return false;
        }

        adjustLoanAmount(-amount);
        return true;
    }

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