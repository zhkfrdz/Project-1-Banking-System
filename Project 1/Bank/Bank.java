package Bank;

import java.util.ArrayList;
import Main.Field;
import Accounts.Account;
import Accounts.CreditAccountModule.CreditAccount;
import Accounts.SavingsAccountModule.SavingsAccount;

public class Bank {
    private final int ID;
    private String name, passcode;
    private final double DEPOSITLIMIT, WITHDRAWLIMIT, CREDITLIMIT;
    private double processingFee;
    private final ArrayList<Account> BANKACCOUNTS;

    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = 50000.0;
        this.WITHDRAWLIMIT = 50000.0;
        this.CREDITLIMIT = 100000.0;
        this.processingFee = 10.0;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    public Bank(int ID, String name, String passcode, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT, double processingFee) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = DEPOSITLIMIT;
        this.WITHDRAWLIMIT = WITHDRAWLIMIT;
        this.CREDITLIMIT = CREDITLIMIT;
        this.processingFee = processingFee;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public String getPasscode() {
        return this.passcode;
    }

    public double getProcessingFee() {
        return this.processingFee;
    }

    public double getDepositLimit() {
        return this.DEPOSITLIMIT;
    }

    public double getWithdrawLimit() {
        return this.WITHDRAWLIMIT;
    }

    public double getCreditLimit() {
        return this.CREDITLIMIT;
    }

    public ArrayList<Account> getBANKACCOUNTS() {
        return this.BANKACCOUNTS;
    }

    public <T> void showAccounts(Class<T> accountType) {
        if (accountType == Account.class) {
            for (Account account : getBANKACCOUNTS()) {
                System.out.println(account);
            }
            return;
        }
        for (Account account : getBANKACCOUNTS()) {
            if (account.getClass() == accountType) {
                System.out.println(account);
            }
        }
    }

    public Account getBankAccount(Bank bank, String accountNum) {
        for (Account account : bank.getBANKACCOUNTS()) {
            if (account.getACCOUNTNUMBER().equals(accountNum)) {
                return account;
            }
        }
        return null;
    }

    public ArrayList<Field<String, ?>> createNewAccount() {
        ArrayList<Field<String, ?>> createNew = new ArrayList<>();

        Field<String, Integer> firstNameField = new Field<>("First Name", String.class, 3, new Field.StringFieldLengthValidator());
        Field<String, Integer> lastNameField = new Field<>("Last name", String.class, 3, new Field.StringFieldLengthValidator());
        Field<String, Integer> emailField = new Field<>("Email", String.class, 12, new Field.StringFieldLengthValidator());
        Field<String, Integer> pinField = new Field<>("Pin", String.class, 4, new Field.StringFieldLengthValidator());

        Field<String, Integer> accountNum = new Field<>("Account Number", String.class, 1, new Field.StringFieldLengthValidator());
        accountNum.setFieldValue("Enter Account Number: ");
        createNew.add(accountNum);
        firstNameField.setFieldValue("Enter First Name: ");
        createNew.add(firstNameField);
        lastNameField.setFieldValue("Enter Last Name: ");
        createNew.add(lastNameField);
        emailField.setFieldValue("Enter Email: ");
        createNew.add(emailField);
        pinField.setFieldValue("Enter Pin (4 digits): ");
        createNew.add(pinField);

        return createNew;
    }

    public CreditAccount createNewCreditAccount() {
        ArrayList<Field<String, ?>> fields = createNewAccount();
        Bank bank = BankLauncher.getLoggedBank();
        CreditAccount credit;

        String accountNum = fields.get(0).getFieldValue();
        String firstName = fields.get(1).getFieldValue();
        String lastName = fields.get(2).getFieldValue();
        String email = fields.get(3).getFieldValue();
        String pin = fields.get(4).getFieldValue();

        credit = new CreditAccount(bank, accountNum, firstName, lastName, email, pin);
        return credit;
    }

    public SavingsAccount createNewSavingsAccount() {
        ArrayList<Field<String, ?>> fields = createNewAccount();
        Bank bank = BankLauncher.getLoggedBank();
        SavingsAccount savings;

        String accountNum = fields.get(0).getFieldValue();
        String firstName = fields.get(1).getFieldValue();
        String lastName = fields.get(2).getFieldValue();
        String email = fields.get(3).getFieldValue();
        String pin = fields.get(4).getFieldValue();

        while (true) {
            Field<Double, Double> balField = new Field<Double,Double>("Credit", Double.class, 500.0, new Field.DoubleFieldValidator());
            balField.setFieldValue("Enter Deposit Amount: ", true);
            if (balField.getFieldValue() <= this.DEPOSITLIMIT) {
                double balance = balField.getFieldValue();
                savings = new SavingsAccount(bank, accountNum, firstName, lastName, email, pin, balance);
                return savings;
            } else {
                System.out.println("Deposit must be less than " + this.DEPOSITLIMIT);
            }
        }
    }

    public void addNewAccount(Account account) {
        boolean exists = accountExist(account.getBank(), account.getACCOUNTNUMBER());

        if (!exists) {
            BANKACCOUNTS.add(account);
            System.out.println("New Account Added Successfully!");
        } else {
            System.out.println("Account number already exists in the bank. Cannot add duplicate account.");
        }
    }

    public static boolean accountExist(Bank bank, String accountNum) {
        ArrayList<Account> accounts = bank.getBANKACCOUNTS();

        for (Account account : accounts) {
            if (account.getACCOUNTNUMBER().equals(accountNum)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String bank = "Bank Info: \n";
        bank += this.getID() + "\n";
        bank += this.getName() + "\n";
        bank += this.getPasscode() + "\n";

        return bank;
    }
}
