package com.mycompany.chatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import org.json.JSONObject;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static int totalMessagesSent = 0;
    private static int globalCounter = 0;
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();

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

    public String checkMessageID() {
        Random rand = new Random();
        long id = 1000000L + (long)(rand.nextDouble() * 9000000L);
        return String.valueOf(id);
    }

    public String checkRecipientCell() {
        if (recipient.matches("^\\+\\d{1,3}\\d{1,10}$") && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        String text = messageText == null? "" : messageText.trim();
        String[] words = text.isEmpty()? new String[]{""} : text.split("\\s+");
        String firstWord = words[0].replaceAll("[^A-Za-z]", "").toUpperCase();
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z]", "").toUpperCase();
        String idPrefix = messageID!= null && messageID.length() >= 2? messageID.substring(0, 2) : "00";
        return idPrefix + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    public String checkMessageLength() {
        if (messageText == null) messageText = "";
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int over = messageText.length() - 250;
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }

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

    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                sentMessages.add(this);
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storedMessages.add(this);
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
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

    // This stays as-is for single message
    public String printMessages() {
        return "Message ID: " + messageID +
               "\nMessage Hash: " + messageHash +
               "\nRecipient: " + recipient +
               "\nMessage: " + messageText;
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public static ArrayList<String> getSentMessages() {
        ArrayList<String> texts = new ArrayList<>();
        for (Message m : sentMessages) {
            texts.add(m.getMessageText());
        }
        return texts;
    }

    public static void addToStoredMessages(String text) {
        Message temp = new Message("+27830000000", text, 0);
        storedMessages.add(temp);
    }

    public String displayLongestMessage() {
        String longest = "";
        for (Message m : storedMessages) {
            if (m.messageText.length() > longest.length()) {
                longest = m.messageText;
            }
        }
        return longest;
    }

    public String searchByMessageID(String id) {
        for (Message m : sentMessages) {
            if (m.messageID.equals(id)) {
                return m.messageText;
            }
        }
        return "Message not found.";
    }

    public String searchByRecipient(String rec) {
        StringBuilder result = new StringBuilder();
        for (Message m : sentMessages) {
            if (m.recipient.equals(rec)) {
                result.append(m.messageText).append("\n");
            }
        }
        return result.toString().trim();
    }

    public String deleteByHash(String hash) {
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).messageHash.equals(hash)) {
                String text = sentMessages.get(i).messageText;
                sentMessages.remove(i);
                return text + " successfully deleted";
            }
        }
        return "Message not found.";
    }

    // RENAMED from printMessages() to printReport() to fix duplicate method error
    public static String printReport() {
        StringBuilder report = new StringBuilder();
        for (Message m : sentMessages) {
            report.append(m.printMessages()).append("\n-------------------\n");
        }
        return report.toString();
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getMessageText() { return messageText; }
    public String getRecipient() { return recipient; }
}