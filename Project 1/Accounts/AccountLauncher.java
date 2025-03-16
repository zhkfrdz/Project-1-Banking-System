package Accounts;

import Accounts.CreditAccountModule.CreditAccount;
import Accounts.CreditAccountModule.CreditAccountLauncher;
import Accounts.SavingsAccountModule.SavingsAccount;
import Accounts.SavingsAccountModule.SavingsAccountLauncher;
import Bank.Bank;
import Bank.BankLauncher;
import Main.Field;
import Main.Main;

/**
 * Handles the login process for accounts and manages account-related operations.
 */
public class AccountLauncher {
    private static Account loggedAccount; // Currently logged-in account
    private static Bank assocBank; // Associated bank for the logged account

    /**
     * Gets the Associated bank for the logged account
     *
     * @return The Associated Bank with the account
     */
    public static Bank getAssocBank() {
        return assocBank;
    }

    /**
     * Checks if an account is currently logged in.
     *
     * @return True if an account is logged in, false otherwise
     */
    private static boolean isLoggedIn() {

        return loggedAccount != null;
    }

    /**
     * Manages the login process for the account.
     *
     * @throws IllegalAccountType if the account type is invalid
     */
    public static void accountLogin() throws IllegalAccountType {

        if (isLoggedIn()) {
            destroyLogSession();
        }

        while (assocBank == null) {
            System.out.println("Select a Bank first");
            assocBank = selectBank();
            if (assocBank == null) {
                System.out.println("Invalid bank selection. Please try again.");
            }
        }

        while (loggedAccount == null) {
            Main.showMenuHeader("Account Login");
            String accountNum = Main.prompt("Input account number: ", true);
            String pin = Main.prompt("Input PIN: ", true);

            loggedAccount = checkCredentials(accountNum, pin);
            if (loggedAccount != null) {
                System.out.println("Login successful.");
                setLogSession(loggedAccount);
                if (loggedAccount instanceof SavingsAccount) {
                    SavingsAccountLauncher.savingsAccountInit();
                } else if (loggedAccount instanceof CreditAccount) {
                    CreditAccountLauncher.creditAccountInit();
                }
            } else {
                System.out.println("Account doesn't exist!");
            }
        }
    }

    /**
     * Prompts the user to select a bank from the list of registered banks.
     *
     * @return The selected bank, or null if no valid selection is made
     */
    private static Bank selectBank() {

        Main.showMenuHeader("Bank Selection");
        BankLauncher.showBanksMenu();
        Field<Integer, Integer> bankID = new Field<Integer,Integer>("ID", Integer.class, -1, new Field.IntegerFieldValidator());
        Field<String, String> bankName = new Field<String,String>("Name", String.class, "", new Field.StringFieldValidator());
        bankID.setFieldValue("Input Bank ID: ");
        bankName.setFieldValue("Input Bank Name: ");

        for (Bank bank : BankLauncher.getBANKS()) {
            if (bank.getID() == bankID.getFieldValue() && bank.getName().equals(bankName.getFieldValue())) {
                System.out.println("Bank selected: " + bankName.getFieldValue());
                return bank;
            }
        }

        System.out.println("Bank does not exist.");
        return null;
    }

    /**
     * Sets the current logged-in account session.
     *
     * @param account The account that is logging in
     */
    private static void setLogSession(Account account) {

        loggedAccount = account;
        System.out.println("Session is set for account: " + account.getOwnerFullName());
    }

    /**
     * Logs out the currently logged-in account.
     */
    private static void destroyLogSession() {

        loggedAccount = null;
        assocBank = null;
    }

    /**
     * Checks the provided credentials against the associated bank's accounts.
     *
     * @param accountNum The account number to check
     * @param pin The PIN to verify
     * @return The account if credentials are valid, null otherwise
     */
    public static Account checkCredentials(String accountNum, String pin) {

        Account selAccount = assocBank.getBankAccount(assocBank, accountNum);
        if (selAccount != null && selAccount.getACCOUNTNUMBER().equals(accountNum) && selAccount.getPin().equals(pin)) {
            return selAccount;
        } else {
            System.out.println("Invalid account number or PIN.");
            return null;
        }
    }

    /**
     * Retrieves the currently logged-in account.
     *
     * @return The logged-in account, or null if no account is logged in
     */
    protected static Account getLoggedAccount() {

        if (isLoggedIn()) {
            return loggedAccount;
        } else {
            System.out.println("There is no logged account.");
            return null;
        }
    }
}