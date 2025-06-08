import java.util.*;
class BankAccount {
    private double balance;
    private List<String> transactionHistory;
    public BankAccount(double initialbal) {
        this.balance = initialbal;
        this.transactionHistory = new ArrayList<>();
    }
    public void deposit(double amount) {
        synchronized(this){
            if (amount > 0) {
                    balance += amount;
                System.out.println("Rs. "+ amount + " deposited successfully.");
            } else {
                System.out.println("Please enter a valid amount to deposit.");
            }
        }
    }
    public void withdraw(double amount) {
        synchronized(this){
            if (amount <= 0) {
                System.out.println("Withdrawal amount must be positive.");
            } else if (amount > balance) {
                System.out.println("Insufficient balance. Current balance: Rs." + balance);
            } else {
                balance -= amount;
                transactionHistory.add("Withdrew Rs." + amount);
                System.out.println("Rs." + amount + " withdrawn successfully.");
            }
        }
    }
    public void checkBalance() {
        synchronized(this){
            System.out.println("Current balance: Rs." + balance);
        }
    }
    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Transaction History:");
            for (String record : transactionHistory) {
                System.out.println(" - " + record);
            }
        }
    }
}
class ATM {
    private BankAccount account;
    private Scanner sc;

    public ATM(BankAccount account) {
        this.account = account;
        this.sc = new Scanner(System.in);
    }
    public void showMenu() {
        int choice;

        do {
            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                     handleDeposit();
                     break;
                case 2:
                     handleWithdraw();
                     break;
                case 3:
                     account.checkBalance();
                     break;
                case 4:
                    account.showTransactionHistory();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 5);
    }
    private void handleDeposit() {
        System.out.print("Enter amount to deposit: Rs.");
        double amount = sc.nextDouble();
        account.deposit(amount);
    }

    private void handleWithdraw() {
        System.out.print("Enter amount to withdraw: Rs.");
        double amount = sc.nextDouble();
        account.withdraw(amount);
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        System.out.println("****Welcome to the ATM Interface****");
        BankAccount userAccount = new BankAccount(1000.0);
        ATM atm = new ATM(userAccount);
        atm.showMenu();
    }
}