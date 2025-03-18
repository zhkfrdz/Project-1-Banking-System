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
    private double PROCESSINGFEE;
    private final ArrayList<Account> BANKACCOUNTS;

    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = 50000.0;
        this.WITHDRAWLIMIT = 50000.0;
        this.CREDITLIMIT = 100000.0;
        this.PROCESSINGFEE = 10.0;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    public Bank(int ID, String name, String passcode, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT, double PROCESSINGFEE) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = DEPOSITLIMIT;
        this.WITHDRAWLIMIT = WITHDRAWLIMIT;
        this.CREDITLIMIT = CREDITLIMIT;
        this.PROCESSINGFEE = PROCESSINGFEE;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public double getDEPOSITLIMIT() {
        return DEPOSITLIMIT;
    }

    public double getWITHDRAWLIMIT() {
        return WITHDRAWLIMIT;
    }

    public double getCREDITLIMIT() {
        return CREDITLIMIT;
    }

    public double getPROCESSINGFEE() {
        return PROCESSINGFEE;
    }

    public ArrayList<Account> getBANKACCOUNTS() {
        return BANKACCOUNTS;
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
        for (Account accs : BANKACCOUNTS) {
            if (accs.getAccountNumber().equals(accountNum)) {
                return accs;
            }
        }
        return null;
    }

    public ArrayList<Field<String, ?>> createNewAccount() throws IllegalArgumentException {
        FieldValidator<String, String> validateString = new Field.StringFieldValidator();
        ArrayList<Field<String, ?>> createNew = new ArrayList<>();

        Main.showMenuHeader("Create New Account");
        // Prompt for first name
        String firstName;
        while (true) {
            try {
                Field<String, String> firstNameField = new Field<>("Enter first name: ", String.class, "3", validateString);
                firstNameField.setFieldValue("Enter first name: ");
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
                lastNameField.setFieldValue("Enter last name: ");
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
                emailField.setFieldValue("Enter email: ");
                email = emailField.getFieldValue();
                if (email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:gmail|yahoo|\\w+\\.)+[a-zA-Z]{2,}$")) {
                    createNew.add(emailField);
                    break;
                } else {
                    System.out.println("Invalid email! Please input a valid email.");
                }
            } catch (IllegalArgumentException exc) {
                System.out.println("Invalid input! Please input a valid email address.");
            }
        }

        // Prompt for account number
        String accountNum;
        while (true) {
            try {
                Field<String, String> accountNumField = new Field<>("Enter account number: ", String.class, "4", validateString);
                accountNumField.setFieldValue("Enter account number: ");
                int num = Integer.parseInt(accountNumField.getFieldValue());
                accountNum = String.format("%d", num);
                if (accountExists(this, accountNum)) {
                    System.out.println("Sorry, this account number already exists. Please input a new one.");
                    continue;
                }
                if (accountNum.length() >= 4) {
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
                pinField.setFieldValue("Enter pin: ");
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

    public CreditAccount createNewCreditAccount() {
        ArrayList<Field<String, ?>> fields = createNewAccount();
        Bank bank = BankLauncher.getLoggedBank();
        CreditAccount credit;

        String firstName = (String) fields.get(0).getFieldValue();
        String lastName = (String) fields.get(1).getFieldValue();
        String email = (String) fields.get(2).getFieldValue();
        String accountNum = (String) fields.get(3).getFieldValue();
        String pin = (String) fields.get(4).getFieldValue();

        credit = new CreditAccount(bank, accountNum, firstName, lastName, email, pin, CREDITLIMIT);
        System.out.println("Account created successfully!");
        return credit;
    }

    public SavingsAccount createNewSavingsAccount() {
        //Create a new account using the common account creation method
        ArrayList<Field<String, ?>> fields = createNewAccount();

        //Create a new Bank instance using account information
        Bank bank = BankLauncher.getLoggedBank();
        SavingsAccount savings;

        String firstName = (String) fields.get(0).getFieldValue();
        String lastName = (String) fields.get(1).getFieldValue();
        String email = (String) fields.get(2).getFieldValue();
        String accountNum = (String) fields.get(3).getFieldValue();
        String pin = (String) fields.get(4).getFieldValue();

        while (true) {
            Field<Double, Double> initialBalanceField = new Field<>("InitialBalance", Double.class, 0.0, new Field.DoubleFieldValidator());
            initialBalanceField.setFieldValue("Enter initial balance: ", true);

            double initialBalance = initialBalanceField.getFieldValue();

            //Validate and create the SavingsAccount if initial balance is non-negative
            if (initialBalance >= 0) {
                savings = new SavingsAccount(bank, accountNum, firstName, lastName, email, pin, initialBalance);
                System.out.println("Account created successfully!");
                return savings;
            } else {
                System.out.println("Initial balance must be non-negative");
                continue;
            }
        }
    }

    public void addNewAccount(Account account) throws IllegalArgumentException, NullPointerException {
        if (account == null) {
            throw new NullPointerException("The account cannot be null.");
        }

        String accountNumStr = account.getAccountNumber().toString();
        if (accountExists(this, accountNumStr)) {
            throw new IllegalArgumentException(
                    "An account with the same account number " +
                            accountNumStr + " already exists!"
            );
        }

        BANKACCOUNTS.add(account);
    }

    public static boolean accountExists(Bank bank, String accountNum) {
        for (Account accs : bank.getBANKACCOUNTS()) {
            if (accs.getAccountNumber().toString().equals(accountNum)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String res = "Bank Name: " + name + "\n";

        int i = 0;
        while (i < BANKACCOUNTS.size()) {
            Account account = BANKACCOUNTS.get(i);
            String accountType = "";

            if (account instanceof CreditAccount) {
                accountType = "Credit Account";

            } else if (account instanceof SavingsAccount) {
                accountType = "Savings Account";

            } else {
                accountType = "Account Type not listed in Bank!";
            }

            res += "Account Type: " + accountType + "\n";
            res += "Account Details: " + account.toString() + "\n";
            i++;
        }
        return res;
    }
}