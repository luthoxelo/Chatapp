package com.mycompany.chatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import org.json.JSONObject;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static int globalCounter = 0;
    private static int totalMessagesSent = 0;

    // --- STATIC LISTS (Message objects) ---
    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>();
    private static List<String> disregardedMessages = new ArrayList<>();

    // --- CONSTRUCTORS ---
    public Message() {
        this.messageID = "";
        this.messageNumber = 0;
        this.recipient = "";
        this.messageText = "";
        this.messageHash = "";
    }

    public Message(String recipient, String messageText, int unused) {
        this.messageID = checkMessageID();
        this.messageNumber = globalCounter++;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // --- ID + HASH ---
    private String checkMessageID() {
        Random rand = new Random();
        long id = 1000000L + (long)(rand.nextDouble() * 9000000L);
        return String.valueOf(id);
    }

    public String createMessageHash() {
        String text = messageText == null ? "" : messageText.trim();
        String[] words = text.isEmpty() ? new String[]{""} : text.split("\\s+");
        String firstWord = words[0].replaceAll("[^A-Za-z]", "").toUpperCase();
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z]", "").toUpperCase();
        String idPrefix = messageID != null && messageID.length() >= 2 ? messageID.substring(0, 2) : "00";
        return idPrefix + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // --- VALIDATION ---
    public String checkMessageLength() {
        if (messageText == null) messageText = "";
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int over = messageText.length() - 250;
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }

    public String checkRecipientCell() {
        if (recipient.matches("^\\+\\d{1,3}\\d{1,10}$") && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // --- SEND / STORE / DISCARD ---
    public String sentMessage(int choice) {
        switch (choice) {
            case 1 -> {
                totalMessagesSent++;
                sentMessages.add(this);
                return "Message successfully sent.";
            }
            case 2 -> {
                disregardedMessages.add(this.messageText);
                return "Press 0 to delete the message.";
            }
            case 3 -> {
                storedMessages.add(this);
                storeMessage();
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid choice.";
            }
        }
    }

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

    // --- LOAD FROM JSON ---
    public static void loadStoredMessages() {
        try (BufferedReader br = new BufferedReader(new FileReader("messages.json"))) {
            String line;
            storedMessages.clear();
            while ((line = br.readLine()) != null) {
                JSONObject obj = new JSONObject(line);
                String text = obj.getString("Message");
                String id = obj.getString("MessageID");
                String rec = obj.getString("Recipient");
                String hash = obj.getString("MessageHash");
                Message m = new Message(rec, text, 0);
                m.messageID = id;
                m.messageHash = hash;
                storedMessages.add(m);
            }
        } catch (IOException e) {
            System.out.println("No previous stored messages found. Initialization complete.");
        }
    }

    // --- SEARCH + DELETE (static, work on sentMessages) ---
    public static String searchByMessageID(String id) {
        for (Message m : sentMessages) {
            if (m.messageID.equals(id)) {
                return m.messageText;
            }
        }
        return "Message not found.";
    }

    public static String searchByRecipient(String rec) {
        StringBuilder result = new StringBuilder();
        for (Message m : sentMessages) {
            if (m.recipient.equals(rec)) {
                result.append(m.messageText).append("\n");
            }
        }
        return result.length() > 0 ? result.toString().trim() : "No messages found for this recipient.";
    }

    public static String deleteByHash(String hash) {
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).messageHash.equals(hash)) {
                String text = sentMessages.get(i).messageText;
                sentMessages.remove(i);
                return "Message: [" + text + "] successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    // --- DISPLAY ---
    public static String displayLongestMessage() {
        String longest = "";
        for (Message m : storedMessages) {
            if (m.messageText.length() > longest.length()) {
                longest = m.messageText;
            }
        }
        return longest.isEmpty() ? "No stored messages found." : longest;
    }

    public static void displayAllStored() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages available.");
            return;
        }
        System.out.println("=== All Stored Messages ===");
        for (Message m : storedMessages) {
            System.out.println("- " + m.messageText);
        }
    }

    // --- REPORTS ---
    public static String printMessages() {
        StringBuilder report = new StringBuilder();
        report.append("=== Message Report ===\n");
        if (sentMessages.isEmpty()) {
            report.append("No sent messages recorded during this session.\n");
            return report.toString();
        }
        for (Message m : sentMessages) {
            report.append("Message Hash: ").append(m.messageHash).append("\n");
            report.append("Recipient:    ").append(m.recipient).append("\n");
            report.append("Message Text: ").append(m.messageText).append("\n");
            report.append("--------------------------------------------------\n");
        }
        return report.toString();
    }

    public static String printReport() {
        return printMessages();
    }

    // --- STATIC HELPERS ---
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public static ArrayList<String> getSentMessages() {
        ArrayList<String> texts = new ArrayList<>();
        for (Message m : sentMessages) {
            texts.add(m.messageText);
        }
        return texts;
    }

    public static void addToStoredMessages(String text) {
        Message temp = new Message("+27830000000", text, 0);
        storedMessages.add(temp);
    }

    public static void clearAll() {
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
        totalMessagesSent = 0;
    }

    // --- SETTERS ---
    public void setMessageText(String messageText) {
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    public void setRecipientCell(String recipient) {
        this.recipient = recipient;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
        this.messageHash = createMessageHash();
    }

    public void setTotalMessages(int totalMessages) {
        this.messageNumber = totalMessages;
        this.messageHash = createMessageHash();
    }

    // --- GETTERS ---
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getMessageText() { return messageText; }
    public String getRecipient() { return recipient; }
}