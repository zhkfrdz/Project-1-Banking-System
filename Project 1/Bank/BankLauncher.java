package Bank;

import Accounts.Account;
import Accounts.CreditAccountModule.CreditAccount;
import Accounts.SavingsAccountModule.SavingsAccount;
import Main.Field;
import Main.Main;
import java.util.ArrayList;

/**
 * Manages the operations related to banks, including login and account management.
 */
public class BankLauncher {
    private final static ArrayList<Bank> BANKS = new ArrayList<>(); // List of registered banks
    private static Bank loggedBank = null; // Currently logged-in bank

    /**
     * Gets the list of registered banks.
     *
     * @return List of banks
     */
    public static ArrayList<Bank> getBANKS() {
        return BANKS;
    }

    /**
     * Gets the currently logged-in bank.
     *
     * @return The logged-in bank
     */
    public static Bank getLoggedBank() {
        return loggedBank;
    }

    /**
     * Checks if a bank is currently logged in.
     *
     * @return True if a bank is logged in, false otherwise
     */
    public static boolean isLogged() {
        return loggedBank != null;
    }

    /**
     * Initializes the bank menu for user interactions.
     */
    public static void bankInit() {
        while (true) {
            Main.showMenuHeader("Bank Menu");
            Main.showMenu(31);
            Main.setOption();

            if (Main.getOption() == 1) {
                showAccounts();
            } else if (Main.getOption() == 2) {
                newAccounts();
            } else if (Main.getOption() == 3) {
                logout();
                break;
            } else {
                System.out.println("Invalid option");
            }
        }
    }

    /**
     * Displays the accounts associated with the logged-in bank.
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
     * Handles the login process for the bank.
     */
    public static void bankLogin() {
        int ID = Integer.parseInt(Main.prompt("Enter Bank ID: ", true));
        String name = Main.prompt("Enter Bank Name: ", true);
        String passcode = Main.prompt("Enter PIN: ", true);

        BankComparator comparator = new BankComparator(); // Assuming a comparator is available

        Bank bank = getBank(comparator, new Bank(ID, name, passcode)); // Use getBank to retrieve the bank

        if (bank != null) {
            setLogSession(bank);
            bankInit();
            return;
        }

        System.out.println("Invalid id or passcode. Please try again.");
    }

    /**
     * Manages the creation of new accounts for the logged-in bank.
     */
    private static void newAccounts() {
        Main.showMenuHeader("New Account Type");
        Main.showMenu(33);
        Main.setOption();

        switch (Main.getOption()) {
            case 1:
                CreditAccount newCreditAcc = getLoggedBank().createNewCreditAccount();
                getLoggedBank().addNewAccount(newCreditAcc);
                break;
            case 2:
                SavingsAccount newSavingAcc = getLoggedBank().createNewSavingsAccount();
                getLoggedBank().addNewAccount(newSavingAcc);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    /**
     * Sets the current logged-in bank session.
     *
     * @param b The bank that is logging in
     */
    private static void setLogSession(Bank b) {
        if (isLogged()) {
            System.out.println("Another Bank Account is currently logged in");
            return;
        }

        loggedBank = b;
    }

    /**
     * Logs out the currently logged-in bank.
     */
    private static void logout() {
        loggedBank = null;
    }

    /**
     * Creates a new bank based on user input.
     *
     * @throws NumberFormatException if the input format is invalid
     */
    public static void createNewBank() throws NumberFormatException {
        Field<Integer,Integer> idField = new Field<>("ID", Integer.class, -1, new Field.IntegerFieldValidator());
        Field<String,String> nameField = new Field<>("Name", String.class, "", new Field.StringFieldValidator());
        Field<String,Integer> passcodeField = new Field<>("Passcode", String.class, 5, new Field.StringFieldLengthValidator());
        Field<Double,Double> depositLimitField = new Field<>("Deposit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double,Double> withdrawLimitField = new Field<>("Witdraw Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double,Double> creditLimitField = new Field<>("Credit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double,Double> processingFeeField = new Field<>("Processing Fee", Double.class, 0.0, new Field.DoubleFieldValidator());

        Bank newBank;

        try {
            idField.setFieldValue("Bank ID: ");
            nameField.setFieldValue("Bank Name: ");
            passcodeField.setFieldValue("Bank Passcode: ");
            depositLimitField.setFieldValue("Deposit Limit(0 for default): ");
            if (depositLimitField.getFieldValue() == 0) {
                int id = idField.getFieldValue();
                String name = nameField.getFieldValue();
                String passcode = passcodeField.getFieldValue();

                newBank = new Bank(id, name, passcode);
            } else {
                withdrawLimitField.setFieldValue("Withdraw Limit: ");
                creditLimitField.setFieldValue("Credit Limit: ");
                processingFeeField.setFieldValue("Processing Fee: ");

                int id = idField.getFieldValue();
                String name = nameField.getFieldValue();
                String passcode = passcodeField.getFieldValue();
                double depositLimit = depositLimitField.getFieldValue();
                double withdrawLimit = withdrawLimitField.getFieldValue();
                double creditLimit = creditLimitField.getFieldValue();
                double processingFee = processingFeeField.getFieldValue();

                newBank = new Bank(id, name, passcode, depositLimit, withdrawLimit, creditLimit, processingFee);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format! Please enter a valid number.");
            return;
        }

        addBank(newBank);
    }

    /**
     * Displays the menu of registered banks.
     */
    public static void showBanksMenu() {
        for (Bank bank : BANKS) {
            System.out.println(bank.toString());
        }
    }

    private static void addBank(Bank b) {
        BANKS.add(b);
    }

    /**
     * Retrieves a bank from the list of registered banks based on a comparator.
     *
     * @param comparator The comparator to use for comparison
     * @param bank The bank to find
     * @return The found bank, or null if not found
     */
    public static Bank getBank(BankComparator comparator, Bank bank) {
        if (getBANKS() == null || bankSize() == 0) {
            return null;
        }
        for (Bank b : getBANKS()) {
            if (comparator.compare(b, bank) == 0) {
                return b;
            }
        }
        return null;
    }

    /**
     * Finds an account by its account number across all registered banks.
     *
     * @param accountNum The account number to search for
     * @return The found Account object, or null if not found
     */
    public static Account findAccount(String accountNum) {
        for (Bank bank : getBANKS()) {
            if (Bank.accountExist(bank, accountNum)){
                return bank.getBankAccount(bank, accountNum);
            }
        }
        return null;
    }

    /**
     * Gets the number of registered banks.
     *
     * @return The size of the bank list
     */
    public static int bankSize() {
        return getBANKS().size();
    }
}
