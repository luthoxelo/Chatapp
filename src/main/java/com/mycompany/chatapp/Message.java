/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import org.json.JSONObject;
/**
 *
 * @author lutho
 */



public class Message {
    // Fields - the data a message holds 【890779647879069590855†L24-L28】
    private String messageID; // 10-digit auto-generated
    private int messageNumber; // from loop counter
    private String recipient; // validated cell number
    private String messageText; // max 250 chars
    private String messageHash; // auto-generated

    private static int totalMessagesSent = 0;

    // Constructor
    public Message(String recipient, String messageText, int messageNumber) {
        this.messageID = checkMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Check message ID - generates 10-digit number 【890779647879069590855†L35-L35】
    public String checkMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    // Check recipient cell - reuse Part 1 logic 【461529554618814786805†L5-L7】
    public String checkRecipientCell() {
        // Reuse Login.java regex: must have international code, max 10 digits after
        if (recipient.matches("^\\+\\d{1,3}\\d{1,10}$") && recipient.length() <= 13) {
            return "Cell phone number successfully captured."; // 【461529554618814786805†L12-L12】
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again."; // 【461529554618814786805†L13-L14】
        }
    }

    // Create message hash: first two digits of ID : messageNum : first+last word uppercase 【890779647879069590855†L35-L35】
    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        return messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // SentMessage method - returns options 【890779647879069590855†L36-L36】
    public String sentMessage(int choice) {
        switch (choice) {
            case 1: // Send
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2: // Disregard
                return "Press 0 to delete message.";
            case 3: // Store
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    // Store message to JSON 【792929775982933398051†L10-L10】
    public void storeMessage() {
        JSONObject json = new JSONObject();
        json.put("MessageID", messageID);
        json.put("MessageNumber", messageNumber);
        json.put("Recipient", recipient);
        json.put("Message", messageText);
        json.put("MessageHash", messageHash);

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(json.toString() + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error storing message to JSON.");
        }
    }

    // Print messages - ID, Hash, Recipient, Message 【792929775982933398051†L8-L8】
    public String printMessages() {
        return "Message ID: " + messageID + 
               "\nMessage Hash: " + messageHash + 
               "\nRecipient: " + recipient + 
               "\nMessage: " + messageText;
    }

    // Return total messages sent 【890779647879069590855†L36-L36】
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Getters for testing
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getMessageText() { return messageText; }
}
    
