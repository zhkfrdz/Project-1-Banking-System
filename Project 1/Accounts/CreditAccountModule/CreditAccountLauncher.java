package Accounts.CreditAccountModule;

import Accounts.Account;
import Accounts.AccountLauncher;
import Accounts.IllegalAccountType;
import Accounts.Transaction.Transactions;
import Main.Main;

import java.util.Objects;

/**
 * Manages the credit account operations and user interactions.
 */
public class CreditAccountLauncher extends AccountLauncher {

    /**
     * Initializes the credit account menu and handles user interactions.
     */
    public static void creditAccountInit() {

        while (true) {
            try {
                Main.showMenuHeader("Credit Account Menu");
                Main.showMenu(41);
                Main.setOption();

                switch (Main.getOption()) {
                    case 1:
                        Main.showMenuHeader("Loan Statement");
                        System.out.println(Objects.requireNonNull(getLoggedAccount()).getLoanStatement());
                        continue;
                    case 2:
                        creditPaymentProcess();
                        continue;
                    case 3:
                        creditRecompenseProcess();
                        continue;
                    case 4:
                        System.out.println(Objects.requireNonNull(getLoggedAccount()).getTransactionsInfo());
                        continue;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (IllegalAccountType err) {
                System.out.println(err.getMessage());
            }
        }
    }

    /**
     * Processes credit payments for the logged-in account.
     *
     * @throws IllegalAccountType If the account type is illegal for the payment.
     */
    private static void creditPaymentProcess() throws IllegalAccountType {

        String accNum = Main.prompt("Account number: ", true);
        double amount = Double.parseDouble(Main.prompt("Amount: ", true));

        Account account = getAssocBank().getBankAccount(getAssocBank(), accNum);
        if (Objects.requireNonNull(getLoggedAccount()).pay(account, amount)) {
            getLoggedAccount().addNewTransaction(getLoggedAccount().getACCOUNTNUMBER(), Transactions.Payment, "A uccessful payment.");
        } else {
            System.out.println("Payment Unsuccessful!");
        }
    }

    /**
     * Processes recompense for the logged-in account.
     */
    private static void creditRecompenseProcess() {

        double amount = Double.parseDouble(Main.prompt("Amount: ", true));

        if (Objects.requireNonNull(getLoggedAccount()).recompense(amount)) {
            getLoggedAccount().addNewTransaction(getLoggedAccount().getACCOUNTNUMBER(), Transactions.Recompense, "A Successful recompense.");
        } else {
            System.out.println("Recompense Unsuccessful!");
        }
    }

    /**
     * Retrieves the logged-in credit account.
     *
     * @return The logged-in CreditAccount, or null if no valid account is found.
     */
    protected static CreditAccount getLoggedAccount() {

        Account account = AccountLauncher.getLoggedAccount();
        if (account instanceof CreditAccount) {
            return (CreditAccount) account;
        } else {
            System.out.println("No logged-in credit account found.");
            return null;
        }
    }
}