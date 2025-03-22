package Bank;

import java.util.ArrayList;
import Accounts.Account;
import Accounts.CreditAccountModule.CreditAccount;
import Main.Field;
import Main.FieldValidator;
import Main.Main;
import Accounts.SavingsAccountModule.SavingsAccount;

public class Bank {
    private int ID;
    private String name, passcode;
    private final double DEPOSITLIMIT, WITHDRAWLIMIT, CREDITLIMIT;
    private double processingFee;
    private final ArrayList<Account> BANKACCOUNTS;

    /**
     * Constructor for initializing a bank with default limits and processing fee.
     * @param ID the bank ID
     * @param name the bank name
     * @param passcode the bank passcode
     */
    public Bank(int ID, String name, String passcode) {

        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = 1000.0;
        this.WITHDRAWLIMIT = 500.0;
        this.CREDITLIMIT = 600.0;
        this.processingFee = 10.0;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    /**
     * Constructor for initializing a bank with specified limits and processing fee.
     * @param ID the bank ID
     * @param name the bank name
     * @param passcode the bank passcode
     * @param DEPOSITLIMIT the deposit limit
     * @param WITHDRAWLIMIT the withdrawal limit
     * @param CREDITLIMIT the credit limit
     * @param PROCESSINGFEE the processing fee
     */
    public Bank(int ID, String name, String passcode, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT, double PROCESSINGFEE) {

        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = DEPOSITLIMIT;
        this.WITHDRAWLIMIT = WITHDRAWLIMIT;
        this.CREDITLIMIT = CREDITLIMIT;
        this.processingFee = PROCESSINGFEE;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    /**
     * Returns the bank ID.
     * @return the bank ID
     */
    public int getID() {

        return ID;
    }

    /**
     * Sets the bank ID.
     * @param iD the bank ID to set
     */
    public void setID(int iD) {

        ID = iD;
    }

    /**
     * Returns the bank name.
     * @return the bank name
     */
    public String getName() {

        return name;
    }

    /**
     * Sets the bank name.
     * @param name the bank name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Returns the bank passcode.
     * @return the bank passcode
     */
    public String getPasscode() {

        return passcode;
    }

    /**
     * Sets the bank passcode.
     * @param passcode the bank passcode to set
     */
    public void setPasscode(String passcode) {

        this.passcode = passcode;
    }

    /**
     * Returns the deposit limit.
     * @return the deposit limit
     */
    public double getDEPOSITLIMIT() {

        return DEPOSITLIMIT;
    }

    /**
     * Returns the withdrawal limit.
     * @return the withdrawal limit
     */
    public double getWITHDRAWLIMIT() {

        return WITHDRAWLIMIT;
    }

    /**
     * Returns the credit limit.
     * @return the credit limit
     */
    public double getCREDITLIMIT() {

        return CREDITLIMIT;
    }

    /**
     * Returns the processing fee.
     * @return the processing fee
     */
    public double getProcessingFee() {

        return processingFee;
    }

    /**
     * Returns the list of bank accounts.
     * @return the list of bank accounts
     */
    public ArrayList<Account> getBANKACCOUNTS() {

        return BANKACCOUNTS;
    }

    /**
     * Displays accounts of a specific type.
     * @param accountType the class type of accounts to display
     */
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

    /**
     * Retrieves a bank account by account number.
     * @param bank the bank to search in
     * @param accountNum the account number to search for
     * @return the account if found, null otherwise
     */
    public Account getBankAccount(Bank bank, String accountNum) {

        for (Account accs : BANKACCOUNTS) {
            if (accs.getAccountNumber().equals(accountNum)) {
                return accs;
            }
        }
        return null;
    }

    /**
     * Prompts for account details and creates a new account.
     * @return a list of fields containing the new account details
     * @throws IllegalArgumentException if input is invalid
     */
    public ArrayList<Field<String, ?>> createNewAccount() throws IllegalArgumentException {

        FieldValidator<String, String> validateString = new Field.StringFieldValidator();
        ArrayList<Field<String, ?>> createNew = new ArrayList<>();

        Main.showMenuHeader("Create New Account");
        // Prompt for first name
        String firstName;
        while (true) {
            try {
                Field<String, String> firstNameField = new Field<>("Enter first name: ", String.class, "3", validateString);
                firstNameField.setFieldValue("Enter First Name: ");
                firstName = firstNameField.getFieldValue();
                if (firstName.length() >= 3) {
                    createNew.add(firstNameField);
                    break;
                }
            } catch (IllegalArgumentException exc) {
                System.out.println("Invalid input! Please input a valid name.");
            }
        }
        // Prompt for last name
        String lastName;
        while (true) {
            try {
                Field<String, String> lastNameField = new Field<>("Enter last name: ", String.class, "3", validateString);
                lastNameField.setFieldValue("Enter Last Name: ");
                lastName = lastNameField.getFieldValue();
                if (lastName.length() >= 3) {
                    createNew.add(lastNameField);
                    break;
                }
            } catch (IllegalArgumentException exc) {
                System.out.println("Invalid input! Please input a valid name.");
            }
        }

        // Prompt for email
        String email;
        while (true) {
            try {
                Field<String, String> emailField = new Field<>("Enter email: ", String.class, "", new Field.StringFieldValidator());
                emailField.setFieldValue("Enter Email: ");
                email = emailField.getFieldValue();
                if (email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:gmail|yahoo|\\w+\\.)+[a-zA-Z]{2,}$")) {
                    createNew.add(emailField);
                    break;
                } else {
                    System.out.println("Invalid Email! Please input a valid Email.");
                }
            } catch (IllegalArgumentException exc) {
                System.out.println("Invalid input! Please input a valid Email Address.");
            }
        }

        // Prompt for account number
        String accountNum;
        while (true) {
            try {
                Field<String, String> accountNumField = new Field<>("Enter account number: ", String.class, "4", validateString);
                accountNumField.setFieldValue("Enter Account Number: ");
                int num = Integer.parseInt(accountNumField.getFieldValue());
                accountNum = String.format("%d", num);
                if (accountExists(this, accountNum)) {
                    System.out.println("Sorry, this account number already exists. Please input a new one.");
                    continue;
                }
                if (!accountNum.isEmpty()) {
                    createNew.add(accountNumField);
                    break;
                }
            } catch (IllegalArgumentException exc) {
                System.out.println("Invalid input! Please input a valid account number.");
            }
        }

        // Prompt for pin
        String pin;
        while (true) {
            try {
                Field<String, String> pinField = new Field<>("Enter pin: ", String.class, "", validateString);
                pinField.setFieldValue("Enter Pin: ");
                pin = pinField.getFieldValue();
                String pinTemp = pin;
                if (pinTemp.length() >= 4) {
                    createNew.add(pinField);
                    pin = pinTemp;
                    break;
                } else {
                    System.out.println("Invalid input! Please input a valid pin (at least 4 digits).");
                }
            } catch (IllegalArgumentException exc) {
                System.out.println("Invalid input! Please input a valid pin (at least 4 digits).");
            }
        }

        return createNew;
    }

    /**
     * Creates a new credit account.
     * @return the newly created CreditAccount
     */
    public CreditAccount createNewCreditAccount() {

        ArrayList<Field<String, ?>> fields = createNewAccount();
        Bank bank = BankLauncher.getLoggedBank();
        CreditAccount credit;

        String firstName = fields.get(0).getFieldValue();
        String lastName = fields.get(1).getFieldValue();
        String email = fields.get(2).getFieldValue();
        String accountNum = fields.get(3).getFieldValue();
        String pin = fields.get(4).getFieldValue();

        credit = new CreditAccount(bank, accountNum, firstName, lastName, email, pin, CREDITLIMIT);
        System.out.println("Account created successfully!");
        return credit;
    }

    /**
     * Creates a new savings account.
     * @return the newly created SavingsAccount
     */
    public SavingsAccount createNewSavingsAccount() {

        //Create a new account using the common account creation method
        ArrayList<Field<String, ?>> fields = createNewAccount();

        //Create a new Bank instance using account information
        Bank bank = BankLauncher.getLoggedBank();
        SavingsAccount savings;

        String firstName = fields.get(0).getFieldValue();
        String lastName = fields.get(1).getFieldValue();
        String email = fields.get(2).getFieldValue();
        String accountNum = fields.get(3).getFieldValue();
        String pin = fields.get(4).getFieldValue();

        while (true) {
            Field<Double, Double> initialBalanceField = new Field<>("InitialBalance", Double.class, 0.0, new Field.DoubleFieldValidator());
            initialBalanceField.setFieldValue("Enter Initial Balance: ", true);

            double initialBalance = initialBalanceField.getFieldValue();

            //Validate and create the SavingsAccount if initial balance is non-negative
            if (initialBalance >= 0) {
                savings = new SavingsAccount(bank, accountNum, firstName, lastName, email, pin, initialBalance);
                System.out.println("Account created successfully!");
                return savings;
            } else {
                System.out.println("Initial Balance must be non-negative");
                continue;
            }
        }
    }

    /**
     * Adds a new account to the bank.
     * @param account the account to add
     * @throws IllegalArgumentException if the account already exists
     * @throws NullPointerException if the account is null
     */
    public void addNewAccount(Account account) throws IllegalArgumentException, NullPointerException {

        if (account == null) {
            throw new NullPointerException("The account cannot be null.");
        }

        String accountNumStr = account.getAccountNumber();
        if (accountExists(this, accountNumStr)) {
            throw new IllegalArgumentException(
                    "An account with the same account number " +
                            accountNumStr + " already exists!"
            );
        }

        BANKACCOUNTS.add(account);
    }

    /**
     * Checks if an account exists in the bank.
     * @param bank the bank to check
     * @param accountNum the account number to check for
     * @return true if the account exists, false otherwise
     */
    public static boolean accountExists(Bank bank, String accountNum) {

        for (Account accs : bank.getBANKACCOUNTS()) {
            if (accs.getAccountNumber().equals(accountNum)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the bank and its accounts.
     * @return a string containing bank details and accounts
     */
    public String toString() {

        StringBuilder res = new StringBuilder("Bank Name: " + name + "\n");

        int i = 0;
        while (i < BANKACCOUNTS.size()) {
            Account account = BANKACCOUNTS.get(i);
            String accountType;

            if (account instanceof CreditAccount) {
                accountType = "Credit Account";

            } else if (account instanceof SavingsAccount) {
                accountType = "Savings Account";

            } else {
                accountType = "Account Type not listed in Bank!";
            }

            res.append("Account Type: ").append(accountType).append("\n");
            res.append("Account Details: ").append(account.toString()).append("\n");
            i++;
        }
        return res.toString();
    }
}
