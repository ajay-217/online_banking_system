package exam;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Transaction {
    private String type;
    private int amount;

    public Transaction(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}

class BankAccount {
    private int balance;
    private List<Transaction> transactionHistory;

    public BankAccount() {
        balance = 0;
        transactionHistory = new ArrayList<>();
    }

    public void deposit(int amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public void withdraw(int amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

public class Online_banking {
    private static Map<String, String> users = new HashMap<>();
    private static Map<String, BankAccount> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static String currentUser;

    public static void main(String[] args) {
        initializeUsers();
        initializeAccounts();

       while (true) {
            System.out.println("\nWelcome to Online Banking System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                	registerUser();
                	break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void initializeUsers() {
        users.put("user1", "password1");
        users.put("user2", "password2");
    }

    private static void initializeAccounts() {
        accounts.put("user1", new BankAccount());
        accounts.put("user2", new BankAccount());
    }

    private static void registerUser() {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        if (!users.containsKey(newUsername)) {
            users.put(newUsername, newPassword);
            accounts.put(newUsername, new BankAccount());
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Username already exists");
        }
    }
    
    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            currentUser = username;
            showMenu();
        } else {
            System.out.println("Invalid username or password");
        }
    }

    private static void showMenu() {
        BankAccount account = accounts.get(currentUser);

        while (true) {
            System.out.println("\nWelcome, " + currentUser);
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. View Transaction History");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    int depositAmount = scanner.nextInt();
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    int withdrawAmount = scanner.nextInt();
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.println("Current balance: " + account.getBalance());
                    break;
                case 4:
                    List<Transaction> transactions = account.getTransactionHistory();
                    System.out.println("Transaction History:");
                    for (Transaction transaction : transactions) {
                        System.out.println(transaction.getType() + " of $" + transaction.getAmount());
                    }
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
