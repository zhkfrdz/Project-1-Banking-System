package Accounts;

import Bank.Bank;
import Bank.BankLauncher;
import Accounts.CreditAccountModule.CreditAccount;
import Accounts.CreditAccountModule.CreditAccountLauncher;
import Accounts.SavingsAccountModule.SavingsAccount;
import Accounts.SavingsAccountModule.SavingsAccountLauncher;
import Main.Main;
import Main.Field;

public class AccountLauncher {
    private static Account loggedAccount;
    private static Bank assocBank;

    public static Bank getAssocBank() {
        return assocBank;
    }

    public static void setAssocBank(Bank assocBank) {
        AccountLauncher.assocBank = assocBank;
    }

    private static boolean isLoggedIn() {
        return loggedAccount != null;
    }

    public static void accountLogin() throws IllegalAccountType {
        Main.showMenuHeader("Account Login");
        if (isLoggedIn()) {
            destroyLogSession();
        }

        while (assocBank == null) {
            System.out.println("Select a bank first");
            assocBank = selectBank();
            if (assocBank == null) {
                System.out.println("Invalid bank selection. Please try again.");
            }
        }

        String accountNum;
        String pin;
        do {
            Main.showMenuHeader("Account Login");
            accountNum = Main.prompt("Enter account number: ", true);
            pin = Main.prompt("Enter PIN: ", true);
            loggedAccount = checkCredentials(accountNum, pin);
            if (loggedAccount == null) {
                System.out.println("Invalid account number or PIN. Please try again.");
            }
        } while (loggedAccount == null);

        if (loggedAccount != null) {
            System.out.println("Login successful.");
            setLogSession(loggedAccount);
            if (loggedAccount.getClass() == SavingsAccount.class) {
                SavingsAccountLauncher.savingsAccountInit();
            }
            else if (loggedAccount.getClass() == CreditAccount.class) {
                CreditAccountLauncher.creditAccountInit();
            }
        }
    }

    private static Bank selectBank() {
        Main.showMenuHeader("Bank Selection");
        BankLauncher.showBanksMenu();
        Field<Integer, Integer> bankID = new Field<Integer,Integer>("ID", Integer.class, -1, new Field.IntegerFieldValidator());
        Field<String, String> bankName = new Field<String,String>("Name", String.class, "", new Field.StringFieldValidator());
        Field<String, String> bankPass = new Field<String,String>("Passcode", String.class, "", new Field.StringFieldValidator());
        bankID.setFieldValue("Enter bank id: ");
        bankName.setFieldValue("Enter bank name: ");
        bankPass.setFieldValue("Enter bank passcode: ");

        for (Bank bank : BankLauncher.getBANKS()) {
            if (bank.getID() == bankID.getFieldValue() && bank.getName().equals(bankName.getFieldValue())) {
                System.out.println("Bank selected: " + bankName.getFieldValue());
                return bank;
            }
        }
        return null;
    }

    private static void setLogSession(Account account) {
        System.out.println("Session created for account number  " + loggedAccount.getAccountNumber());
    }

    private static void destroyLogSession() {
        // Check if a user is logged in
        if (isLoggedIn()) {
            // Log the destruction of the log session for the currently logged account
            System.out.println("Destroying log session for account: " + loggedAccount.getAccountNumber());
            // Reset the loggedAccount to null
            loggedAccount = null;
            assocBank = null;
            // Print a message indicating the successful destruction of the log session
            System.out.println("Log session destroyed.");
        }

        else {
            // Print a message indicating that no user is logged in, and log session destruction is not required
            System.out.println("No user logged in. Log session destruction not required.");
        }
    }

    public static Account checkCredentials(String accountNum, String pin) {
        Account selAccount = assocBank.getBankAccount(assocBank, accountNum);
        if (selAccount != null && selAccount.getAccountNumber().equals(accountNum) && selAccount.getPin().equals(pin)) {
            return selAccount;
        } else {
            System.out.println("Invalid account number or PIN for account " + accountNum + ".");
            return null;
        }
    }

    protected static Account getLoggedAccount() {
        return loggedAccount;
    }
}