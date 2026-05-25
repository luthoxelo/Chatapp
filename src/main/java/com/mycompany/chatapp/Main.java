/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;
import java.util.ArrayList;
import java.util.Scanner;
import com.mycompany.chatapp.Message;
/**
 *
 * @author Student
 */
public class Main {
    public static void main(String[]args) {

        // Scanner allows the user to enter information
        Scanner input = new Scanner(System.in);

        // Create an object of the Login class
        Login login = new Login();

        // Store messages
        ArrayList<Message> messages = new ArrayList<>();

        int totalMessagesSent = 0;

        // ================= REGISTRATION SECTION =================
System.out.println("=== USER REGISTRATION ===");

String username;
String password;
String phone;

// ---------- USERNAME LOOP ----------
while (true) {

    System.out.print("Enter a username: ");
    username = input.nextLine();

    if (username.length() >= 5) {

        System.out.println("Username successfully captured.");
        break;

    } else {

        System.out.println("Username is not correctly formatted.");
        System.out.println("Please ensure that the username contains at least 5 characters.");
    }
}

// ---------- PASSWORD LOOP ----------
while (true) {

    System.out.print("Enter a password: ");
    password = input.nextLine();

    // Password must contain:
    // at least 8 characters
    // one capital letter
    // one number

    boolean hasCapital = false;
    boolean hasNumber = false;

    for (int i = 0; i < password.length(); i++) {

        char ch = password.charAt(i);

        if (Character.isUpperCase(ch)) {
            hasCapital = true;
        }

        if (Character.isDigit(ch)) {
            hasNumber = true;
        }
    }

    if (password.length() >= 8 && hasCapital && hasNumber) {

        System.out.println("Password successfully captured.");
        break;

    } else {

        System.out.println("Password is not correctly formatted.");
        System.out.println("Password must contain:");
        System.out.println("- At least 8 characters");
        System.out.println("- One capital letter");
        System.out.println("- One number");
    }
}

// ---------- PHONE NUMBER LOOP ----------
while (true) {

    System.out.print("Enter your South African phone number (+27...): ");
    phone = input.nextLine();

    if (phone.startsWith("+27") && phone.length() == 12) {

        System.out.println("Cell phone number successfully added.");
        break;

    } else {

        System.out.println("Cell phone number incorrectly formatted.");
        System.out.println("Number must start with +27 and contain 12 characters.");
    }
}

// ---------- REGISTER USER ----------
String response = login.registerUser(username, password);

// ---------- SHOW FINAL RESPONSE ----------
System.out.println(response);

        // ================= LOGIN SECTION =================
        System.out.println("\n=== USER LOGIN ===");

        System.out.print("Enter your username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter your password: ");
        String loginPassword = input.nextLine();

        // Check login
        boolean loggedIn = login.loginUser(loginUsername, loginPassword);

        // Display login message
        String loginMessage = login.returnLoginStatus(loggedIn);
        System.out.println(loginMessage);

        // ================= MESSAGING SECTION =================
        if (loggedIn) {

            System.out.println("Welcome to ChatApp.");

            int choice;

            do {
                System.out.println("\n1. Send Message");
                System.out.println("2. Show Recent Messages");
                System.out.println("3. Quit");

                System.out.print("Choose an option: ");
                choice = input.nextInt();
                input.nextLine();

                // ================= SEND MESSAGE =================
                if (choice == 1) {

                    System.out.print("How many messages do you want to send? ");
                    int numMessages = input.nextInt();
                    input.nextLine();

                    for (int i = 0; i < numMessages; i++) {

                        System.out.println("\n--- Message " + (i + 1) + " ---");

                        System.out.print("Enter recipient number: ");
                        String recipient = input.nextLine();

                        System.out.print("Enter message: ");
                        String text = input.nextLine();

                        // Check message length
                        if (text.length() > 250) {

                            int over = text.length() - 250;

                            System.out.println(
                                "Message exceeds 250 characters by "
                                + over
                                + ". Please reduce the size."
                            );

                            i--;
                            continue;
                        }

                        // Create message object
                       Message msg = new Message(recipient, text, i + 1);

                        // Display message details
                        System.out.println(msg.checkRecipientCell());
                        System.out.println("Message ID: " + msg.getMessageID());
                        System.out.println("Message Hash: " + msg.createMessageHash());

                        // User choice
                        System.out.println("Choose:");
                        System.out.println("1 - Send");
                        System.out.println("2 - Disregard");
                        System.out.println("3 - Store");

                        int action = input.nextInt();
                        input.nextLine();

                        String result = msg.sentMessage(action);

                        System.out.println(result);

                        // Save sent messages
                        if (action == 1) {

                            totalMessagesSent++;
                            messages.add(msg);

                        } else if (action == 2) {

                            messages.add(msg);
                            
                        }
                    }

                }
                // ================= SHOW RECENT MESSAGES =================
                else if (choice == 2) {

                    if (messages.isEmpty()) {

                        System.out.println("No messages available.");

                    } else {

                        for (Message m : messages) {

                            System.out.println(m.printMessages());
                            System.out.println("-------------------");
                        }
                    }
                }

            } while (choice != 3);

            System.out.println("\nTotal messages sent: " + totalMessagesSent);
            System.out.println("Goodbye!");

        } else {

            System.out.println("Login failed. Exiting program.");
        }

        input.close();
    }
}