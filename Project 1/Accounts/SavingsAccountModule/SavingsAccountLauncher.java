package Accounts.SavingsAccountModule;

import Accounts.Account;
import Accounts.AccountLauncher;
import Accounts.IllegalAccountType;
import Accounts.Transaction.Transactions;
import Bank.Bank;
import Bank.BankLauncher;
import Main.Main;

import java.util.Objects;

/**
 * Manages the savings account operations and user interactions.
 */
public class SavingsAccountLauncher extends AccountLauncher {

    /**
     * Initializes the savings account menu and handles user interactions.
     */
    public static void savingsAccountInit() {

        while (true) {
            try {
                Main.showMenuHeader("Savings Account Menu");
                Main.showMenu(51);
                Main.setOption();

                switch (Main.getOption()) {
                    case 1:
                        Main.showMenuHeader("Balance Statement");
                        System.out.println(Objects.requireNonNull(getLoggedAccount()).getAccountBalanceStatement());
                        continue;
                    case 2:
                        depositProcess();
                        continue;
                    case 3:
                        withdrawProcess();
                        continue;
                    case 4:
                        fundTransferProcess();
                        continue;
                    case 5:
                        System.out.println(Objects.requireNonNull(getLoggedAccount()).getTransactionsInfo());
                        continue;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid Option");
                }
            } catch (IllegalAccountType err) {
                System.out.println(err.getMessage());
            }
        }
    }

    /**
     * Processes deposits for the logged-in savings account.
     */
    private static void depositProcess() {

        SavingsAccount savingsAccount = getLoggedAccount();

        if (savingsAccount == null) {
            System.out.println("No Account found!");
            return;
        }

        double depositAmount;

        while (true) {
            String depositAmount_str = Main.prompt("Input Deposit amount: ", true);

            try {
                depositAmount = Double.parseDouble(depositAmount_str);
                if (depositAmount <= 0) {
                    System.out.println("Invalid amount!");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please Input a valid amount!");
            }
        }

        boolean depositSuccess = savingsAccount.cashDeposit(depositAmount);
        if (depositSuccess) {
            System.out.println("Deposit successful!");
            System.out.println("Balance: " + savingsAccount.getBalance());
            getLoggedAccount().addNewTransaction(getLoggedAccount().getACCOUNTNUMBER(), Transactions.Deposit, "A Successful deposit.");
        } else {
            System.out.println("Deposit failed. Please try again.");
        }
    }

    /**
     * Processes withdrawals for the logged-in savings account.
     */
    private static void withdrawProcess() {

        SavingsAccount loggedAccount = getLoggedAccount();
        if (loggedAccount != null) {
            loggedAccount.toString();
            String withdrawAmount = Main.prompt("Input the amount to withdraw: ", true);
            double amount = Double.parseDouble(withdrawAmount);

            if (amount <= 0) {
                System.out.println("Invalid amount. Please Input a positive value.");
                return;
            }

            if (loggedAccount.getBalance() >= amount) {
                if (loggedAccount.withdrawal(amount)) {
                    System.out.println("Withdrawal successful");
                    System.out.println("Balance: " + loggedAccount.getBalance());
                    getLoggedAccount().addNewTransaction(getLoggedAccount().getACCOUNTNUMBER(), Transactions.Withdraw, "A Successful withdraw.");
                } else {
                    System.out.println("Withdrawal failed");
                }
            } else {
                System.out.println("Insufficient balance for withdrawal.");
            }
        } else {
            System.out.println("No account logged in.");
        }
    }

    private static void fundTransferProcess() throws IllegalAccountType {
        SavingsAccount loggedAccount = getLoggedAccount();

        System.out.println("[1]. Internal transfer \n[2]. External transfer");
        Main.setOption();

        switch (Main.getOption()) {
            case 1:
                String internalAccNum = Main.prompt("Account Number: ", true);
                double internalAmount = Double.parseDouble(Main.prompt("Amount: ", true));

                assert loggedAccount != null;
                SavingsAccount internalAccount = (SavingsAccount) loggedAccount.getBank().getBankAccount(loggedAccount.getBank(), internalAccNum);
                if (loggedAccount.transfer(internalAccount, internalAmount)) {
                    Objects.requireNonNull(getLoggedAccount()).addNewTransaction(getLoggedAccount().getACCOUNTNUMBER(), Transactions.FundTransfer, "A successful fund transfer.");
                } else {
                    System.out.println("Transfer unsuccessful!");
                }
                break;

            case 2:
                int externalBankID = Integer.parseInt(Main.prompt("Bank ID: ", true));
                String externalAccNum = Main.prompt("Account Number: ", true);
                double externalAmount = Double.parseDouble(Main.prompt("Amount: ", true));

                for (Bank bank : BankLauncher.getBANKS()) {
                    if (bank.getID() == externalBankID) {
                        Account externalAccount = bank.getBankAccount(bank, externalAccNum);
                        assert loggedAccount != null;
                        if (loggedAccount.transfer(bank, externalAccount, externalAmount)) {
                            Objects.requireNonNull(getLoggedAccount()).addNewTransaction(getLoggedAccount().getACCOUNTNUMBER(), Transactions.FundTransfer, "A successful fund transfer.");
                        } else {
                            System.out.println("Transfer Unsuccessful!");
                        }
                    }
                }
                break;
            default:
                System.out.println("Invalid Option!");
        }
    }

    /**
     * Retrieves the logged-in savings account.
     *
     * @return The logged-in SavingsAccount, or null if no valid account is found.
     */
    protected static SavingsAccount getLoggedAccount() {

        Account account = AccountLauncher.getLoggedAccount();
        if (account instanceof SavingsAccount) {
            return (SavingsAccount) account;
        } else {
            System.out.println("No Logged-in savings account found.");
            return null;
        }
    }
}