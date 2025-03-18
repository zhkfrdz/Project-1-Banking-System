package Bank;

import java.util.ArrayList;
import Accounts.Account;
import Accounts.CreditAccountModule.CreditAccount;
import Accounts.SavingsAccountModule.SavingsAccount;
import Main.Menu;
import Main.Main;
import Main.Field;


public class BankLauncher {
    private static ArrayList<Bank> BANKS = new ArrayList<>();
    private static Bank loggedBank = null;

    /**
     * Checks if a bank is currently logged in.
     * @return true if a bank is logged in, false otherwise
     */
    public static boolean isLogged() {

        return loggedBank != null;
    }

    /**
     * Initializes the bank login process and displays the bank menu.
     */
    public static void bankInit() {

        bankLogin();
        if (isLogged()) {
            boolean menuContinue = true;

            while (menuContinue) {
                Main.showMenuHeader("Bank Menu");
                Main.showMenu(Menu.BankMenu.menuIdx);

                try {
                    Main.setOption();
                    int choice = Main.getOption();

                    switch (choice) {
                        case 1:
                            showAccounts();
                            break;
                        case 2:
                            newAccounts();
                            break;
                        case 3:
                            logout();
                            menuContinue = false;
                            break;
                        default:
                            System.out.println("Invalid option! Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                }
            }
        } else {
            System.out.println("Please login to your bank account.");
        }
    }

    /**
     * Displays the accounts of the logged-in bank.
     */
    public static void showAccounts() {

        while (true) {
            Main.showMenuHeader("Show Accounts");
            Main.showMenu(32);
            Main.setOption();

            switch (Main.getOption()) {
                case 1:
                    Main.showMenuHeader("Credit Accounts");
                    getLoggedBank().showAccounts(CreditAccount.class);
                    continue;
                case 2:
                    Main.showMenuHeader("Savings Accounts");
                    getLoggedBank().showAccounts(SavingsAccount.class);
                    continue;
                case 3:
                    Main.showMenuHeader("Accounts");
                    getLoggedBank().showAccounts(Account.class);
                    continue;
                case 4:
                    return;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    /**
     * Prompts for bank name and PIN to log in.
     */
    public static void bankLogin() {

        Main.showMenuHeader("Bank Login");
        String bankName = Main.prompt("Enter bank name: ", true);
        String pin = Main.prompt("Enter PIN: ", true);

        for (Bank bank : BANKS) {
            if (bank.getName().equals(bankName) && bank.getPasscode().equals(pin)) {
                setLogSession(bank);
                break;
            }
        }
        if (loggedBank == null) {
            System.out.println("Invalid bank name or PIN. Please try again.");
        }
    }

    /**
     * Prompts for account type and creates a new account.
     */
    private static void newAccounts() {

        Main.showMenuHeader("New Account Type");
        Main.showMenu(33);
        Main.setOption();
        switch (Main.getOption()) {
            case 1: // Creates and adds a new Credit Account to the logged-in bank.
                CreditAccount newCreditAcc = getLoggedBank().createNewCreditAccount();
                getLoggedBank().addNewAccount(newCreditAcc);
                break;
            case 2: // Creates and adds a new Savings Account to the logged-in bank.
                SavingsAccount newSavingAcc = getLoggedBank().createNewSavingsAccount();
                getLoggedBank().addNewAccount(newSavingAcc);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    /**
     * Sets the logged-in bank session.
     * @param b the bank to log in
     */
    private static void setLogSession(Bank b) {

        // Checks if another bank account is already logged in
        if (isLogged()) {
            System.out.println("Another bank account is currently logged in");
            return;
        }
        // Sets the provided bank as the logged-in bank
        setLoggedBank(b);
        System.out.println("Bank account logged in successfully");
    }

    // Janos and Mia here
    /**
     * Logs out the current bank session.
     */
    private static void logout() {

        loggedBank = null;
        System.out.println("Logout successful. Session destroyed.");
    }

    /**
     * Prompts for details to create a new bank.
     */
    public static void createNewBank() {

        Main.showMenuHeader("Create New Bank");
        Field<Integer,Integer> idField = new Field<>("ID", Integer.class, -1, new Field.IntegerFieldValidator());
        Field<String,String> nameField = new Field<>("Name", String.class, "", new Field.StringFieldValidator());
        Field<String,Integer> passcodeField = new Field<>("Passcode", String.class, 5, new Field.StringFieldLengthValidator());
        Field<Double,Double> depositLimitField = new Field<>("Deposit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double,Double> withdrawLimitField = new Field<>("Witdraw Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double,Double> creditLimitField = new Field<>("Credit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double,Double> processingFeeField = new Field<>("Processing Fee", Double.class, 0.0, new Field.DoubleFieldValidator());

        try {
            idField.setFieldValue("Bank ID: ");
            nameField.setFieldValue("Bank Name: ");
            passcodeField.setFieldValue("Bank Passcode: ");
            depositLimitField.setFieldValue("Deposit Limit (0 for default): ");
            withdrawLimitField.setFieldValue("Withdraw Limit (0 for default): ");
            creditLimitField.setFieldValue("Credit Limit (0 for default): ");
            processingFeeField.setFieldValue("Processing Fee (0 for default): ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format! Please enter a valid number.");
            return;
        }

        int id = idField.getFieldValue();
        String name = nameField.getFieldValue();
        String passcode = passcodeField.getFieldValue();
        double depositLimit = depositLimitField.getFieldValue();
        double withdrawLimit = withdrawLimitField.getFieldValue();
        double creditLimit = creditLimitField.getFieldValue();
        double processingFee = processingFeeField.getFieldValue();

        Bank newBank;
        if (depositLimit == 0.0 && withdrawLimit == 0.0 && creditLimit == 0.0 && processingFee == 0.0) {
            newBank = new Bank(id, name, passcode);
        } else {
            newBank = new Bank(id, name, passcode, depositLimit, withdrawLimit, creditLimit, processingFee);
        }

        System.out.println("Bank created successfully.");
        addBank(newBank);
    }

    /**
     * Displays the list of registered banks.
     */
    public static void showBanksMenu() {

        if (BANKS == null || BANKS.isEmpty()) {
            System.out.println("No banks registered or created.");
            return;
        }

        System.out.println("Registered Banks:");
        System.out.println("-------------------------------------");

        for (int i = 0; i < BANKS.size(); i++) {
            Bank bank = BANKS.get(i);
            System.out.println((i + 1) + ". " + bank.getName());
            System.out.println("   ID: " + bank.getID());
            System.out.println("-------------------------------------");
        }
    }

    /**
     * Adds a new bank to the list of banks.
     * @param b the bank to add
     */
    private static void addBank(Bank b) {

        BANKS.add(b);
    }

    // Janos and Mia here
    /**
     * Retrieves a bank based on a comparator.
     * @param comparator the comparator to use for comparison
     * @param bank the bank to find
     * @return the matching bank, or null if not found
     */
    public static Bank getBank(Comparator<Bank> comparator, Bank bank) {

        for (Bank registeredBank : BANKS) {
            if (comparator.compare(registeredBank, bank) == 0) {
                return registeredBank;
            }
        }
        return null;
    }

    /**
     * Finds an account by account number across all banks.
     * @param accountNum the account number to find
     * @return the account if found, null otherwise
     */
    public static Account findAccount(String accountNum) {

        for (Bank bank : getBANKS()) {
            if (Bank.accountExists(bank, accountNum) == true){
                return bank.getBankAccount(bank, accountNum);
            }
        }
        // If the account number is not found in any bank, return null
        return null;
    }

    /**
     * Returns the number of registered banks.
     * @return the size of the bank list
     */
    public static int bankSize() {

        return BANKS.size();
    }

    /**
     * Returns the list of registered banks.
     * @return the list of banks
     */
    public static ArrayList<Bank> getBANKS() {

        if (BANKS == null) {
            BANKS = new ArrayList<>();
            return BANKS;
        } else if (BANKS.isEmpty()) {
            return BANKS;
        }
        return BANKS;
    }

    /**
     * Returns the currently logged-in bank.
     * @return the logged bank
     */
    public static Bank getLoggedBank() {

        return loggedBank;
    }

    /**
     * Sets the currently logged-in bank.
     * @param loggedBank the bank to set as logged in
     */
    public static void setLoggedBank(Bank loggedBank) {

        BankLauncher.loggedBank = loggedBank;
    }
}
