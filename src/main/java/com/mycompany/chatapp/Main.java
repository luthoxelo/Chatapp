package com.mycompany.chatapp;

import java.util.ArrayList;
import java.util.Scanner;
import com.mycompany.chatapp.Message;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login login = new Login();
        int totalMessagesSent = 0;

        // === REGISTRATION ===
        System.out.println("=== USER REGISTRATION ===");

        String username;
        String password;
        String phone;

        while (true) {
            System.out.print("Enter a username: ");
            username = input.nextLine();
            if (login.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted.");
                System.out.println("Please ensure that the username contains an underscore and is no more than five characters.");
            }
        }

        while (true) {
            System.out.print("Enter a password: ");
            password = input.nextLine();
            if (login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted.");
                System.out.println("Password must contain:");
                System.out.println("- At least 8 characters");
                System.out.println("- One capital letter");
                System.out.println("- One number");
                System.out.println("- One special character");
            }
        }

        while (true) {
            System.out.print("Enter your South African phone number (+27...): ");
            phone = input.nextLine();
            if (login.checkCellphoneNumber(phone)) {
                System.out.println("Cell phone number successfully added.");
                break;
            } else {
                System.out.println("Cell phone number incorrectly formatted.");
                System.out.println("Number must start with +27 and contain 12 characters.");
            }
        }

        String response = login.registerUser(username, password, phone);
        System.out.println(response);

        if (!response.equals("User registered successfully.")) {
            System.out.println("Registration failed. Exiting program.");
            input.close();
            return;
        }

        // === LOGIN ===
        System.out.println("\n=== USER LOGIN ===");
        System.out.print("Enter your username: ");
        String loginUsername = input.nextLine();
        System.out.print("Enter your password: ");
        String loginPassword = input.nextLine();

        boolean loggedIn = login.loginUser(loginUsername, loginPassword);
        System.out.println(login.returnLoginStatus(loggedIn));

        if (!loggedIn) {
            System.out.println("Login failed. Exiting program.");
            input.close();
            return;
        }

        // === MESSAGING ===
        System.out.println("Welcome to ChatApp.");
        Message.loadStoredMessages();

        int mainChoice = -1;
        while (mainChoice != 3) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Select an option: ");

            mainChoice = input.nextInt();
            input.nextLine();

            switch (mainChoice) {
                case 1:
                    System.out.print("How many messages do you want to send? ");
                    int numMessages = input.nextInt();
                    input.nextLine();

                    for (int i = 0; i < numMessages; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " ---");
                        System.out.print("Enter recipient number: ");
                        String recipient = input.nextLine();
                        System.out.print("Enter message: ");
                        String text = input.nextLine();

                        if (text.length() > 250) {
                            int over = text.length() - 250;
                            System.out.println("Message exceeds 250 characters by " + over + ". Please reduce the size.");
                            i--;
                            continue;
                        }

                        Message msg = new Message(recipient, text, 0);
                        System.out.println(msg.checkRecipientCell());
                        System.out.println("Message ID: " + msg.getMessageID());
                        System.out.println("Message Hash: " + msg.getMessageHash());

                        System.out.println("Choose:");
                        System.out.println("1 - Send");
                        System.out.println("2 - Disregard");
                        System.out.println("3 - Store");

                        int action = input.nextInt();
input.nextLine();

String result = msg.sentMessage(action);
System.out.println(result);

if (action == 1) {
    totalMessagesSent++;
} else if (action == 2) {
    System.out.print("Enter 0 to confirm disregard: ");
    int confirm = input.nextInt();
    input.nextLine();
    if (confirm == 0) {
        System.out.println("Message successfully disregarded.");
    } else {
        System.out.println("Disregard cancelled. Message kept.");
    }
}
    }
    break;
case 2:
    System.out.println(Message.printMessages());
    break;
case 3:
    System.out.println("\nTotal messages sent: " + totalMessagesSent);
    System.out.println("Exiting application. Goodbye!");
    break;
case 4:
    storedMessagesMenu(input);
    break;
default:
    System.out.println("Invalid option. Try again.");
}
    }
    input.close();
}

    // === STORED MESSAGES SUB-MENU ===
    private static void storedMessagesMenu(Scanner input) {
        String subChoice;
        do {
            System.out.println("\n=== STORED MESSAGES SUB-MENU ===");
            System.out.println("a) Display all stored messages");
            System.out.println("b) Display longest message");
            System.out.println("c) Search by message ID");
            System.out.println("d) Search by recipient");
            System.out.println("e) Delete by message hash");
            System.out.println("f) Display full report");
            System.out.println("q) Return to Main Menu");
            System.out.print("Choose an option: ");

            subChoice = input.nextLine().toLowerCase().trim();

            switch (subChoice) {
                case "a":
                    Message.displayAllStored();
                    break;
                case "b":
                    System.out.println("Longest Message:\n" + Message.displayLongestMessage());
                    break;
                case "c":
                    System.out.print("Enter Message ID to search: ");
                    String idInput = input.nextLine();
                    System.out.println(Message.searchByMessageID(idInput));
                    break;
                case "d":
                    System.out.print("Enter Recipient cell number: ");
                    String recInput = input.nextLine();
                    System.out.println(Message.searchByRecipient(recInput));
                    break;
                case "e":
                    System.out.print("Enter Message Hash to delete: ");
                    String hashInput = input.nextLine();
                    System.out.println(Message.deleteByHash(hashInput));
                    break;
                case "f":
                    System.out.println(Message.printMessages());
                    break;
                case "q":
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid selection. Please use letters a - f or q.");
            }
        } while (!subChoice.equalsIgnoreCase("q"));
    }
}