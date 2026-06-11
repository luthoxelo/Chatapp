/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author lutho
 */
public class MessageTest {

    Message msg1, msg2, msg3, msg4, msg5;

    @BeforeEach
    public void setUp() {
        Message.clearAll();
        msg1 = new Message("+27834557896", "Did you get the cake?", 0);
        msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 0);
        msg3 = new Message("+27831234567", "Hows it going?", 0);
        msg4 = new Message("+27838884567", "It is dinner time!", 0);
        msg5 = new Message("+27838884567", "Ok, I am leaving without you.", 0);
        msg4.setMessageID("0838884567");
    }

    @Test
    public void testMessageLengthValid() {
        Message msg = new Message();
        msg.setMessageText("Hi Mike, can you join us for dinner tonight?");
        String result = msg.checkMessageLength();
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testMessageLengthInvalid() {
        Message msg = new Message();
        String longMsg = "A".repeat(260);
        msg.setMessageText(longMsg);
        String result = msg.checkMessageLength();
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", result);
    }

    @Test
    public void testRecipientCellValid() {
        Message msg = new Message();
        msg.setRecipientCell("+27718693002");
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testRecipientCellInvalid() {
        Message msg = new Message();
        msg.setRecipientCell("08575975889");
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testCreateMessageHash_viaConstructor() {
        Message msg = new Message("+27834557896", "Hello World", 0);
        String resultHash = msg.getMessageHash();
        assertNotNull(resultHash);
    }

    @Test
    public void testSentMessage_Send() {
        Message msg = new Message();
        String result = msg.sentMessage(1);
        assertEquals("Message successfully sent.", result);
    }

    @Test
    public void testSentMessage_Discard() {
        Message msg = new Message();
        String result = msg.sentMessage(2);
        assertEquals("Press 0 to delete the message.", result);
    }

    @Test
    public void testSentMessage_Store() {
        Message msg = new Message();
        String result = msg.sentMessage(3);
        assertEquals("Message successfully stored.", result);
    }

    @Test
    public void testSentMessagesArray_correctlyPopulated() {
        msg1.sentMessage(1);
        msg4.sentMessage(1);
        assertTrue(Message.getSentMessages().contains("Did you get the cake?"));
        assertTrue(Message.getSentMessages().contains("It is dinner time!"));
    }

    @Test
    public void testSentMessagesArray_correctlyPopulated_viaReport() {
        Message m1 = new Message("+27834557896", "Did you get the cake?", 0);
        m1.sentMessage(1);
        Message m2 = new Message("+27838884567", "It is dinner time!", 0);
        m2.sentMessage(1);

        String report = Message.printMessages();
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time!"));
    }

    @Test
    public void testDisplayLongestMessage_returnsCorrectMessage() {
        Message.addToStoredMessages("Did you get the cake?");
        Message.addToStoredMessages("Where are you? You are late! I have asked you to be on time.");
        Message.addToStoredMessages("Hows it going?");
        Message.addToStoredMessages("It is dinner time!");
        Message.addToStoredMessages("Ok, I am leaving without you.");
        String longest = Message.displayLongestMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testDisplayLongestMessage_withTwoMessages() {
        Message.addToStoredMessages("Short msg");
        Message.addToStoredMessages("Where are you? You are late! I have asked you to be on time.");
        String longestResult = Message.displayLongestMessage();
        assertNotNull(longestResult);
        assertEquals("Where are you? You are late! I have asked you to be on time.", longestResult);
    }

    @Test
    public void testSearchByMessageID_returnsCorrectMessage() {
        msg4.sentMessage(1);
        String result = Message.searchByMessageID("0838884567");
        assertNotNull(result);
        assertFalse(result.equals("Message not found."));
    }

    @Test
    public void testSearchByMessageID_viaGetID() {
        Message msg = new Message("+27838884567", "It is dinner time!", 0);
        msg.sentMessage(1);
        String result = Message.searchByMessageID(msg.getMessageID());
        assertNotNull(result);
        assertFalse(result.equals("Message not found."));
    }

    @Test
    public void testSearchByRecipient_returnsAllMatchingMessages() {
        msg1.sentMessage(1);
        msg2.sentMessage(1);
        msg3.sentMessage(1);
        msg4.sentMessage(1);
        msg5.sentMessage(1);
        String result = Message.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testSearchByRecipient_withTwoMessages() {
        Message m1 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 0);
        m1.sentMessage(1);
        Message m2 = new Message("+27838884567", "Ok, I am leaving without you.", 0);
        m2.sentMessage(1);
        String searchResult = Message.searchByRecipient("+27838884567");
        assertTrue(searchResult.contains("Where are you?"));
        assertTrue(searchResult.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteByHash_removesCorrectMessage() {
        msg2.sentMessage(1);
        String hash = msg2.getMessageHash();
        String result = Message.deleteByHash(hash);
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("successfully deleted"));
    }

    @Test
    public void testDeleteByHash_shortMessage() {
        Message msg = new Message("+27838884567", "Where are you? You are late!", 0);
        msg.sentMessage(1);
        String targetHash = msg.getMessageHash();
        String deletionReport = Message.deleteByHash(targetHash);
        assertTrue(deletionReport.contains("successfully deleted"));
    }

    @Test
    public void testDisplayReport_containsRequiredFields() {
        msg1.sentMessage(1);
        String report = Message.printReport();
        assertTrue(report.contains(msg1.getMessageHash()));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
    }

    @Test
    public void testDisplayReport_containsRequiredFields_viaTestData() {
        Message msg = new Message("+27834557896", "Test Report Data", 0);
        msg.sentMessage(1);
        String generatedReport = Message.printMessages();
        assertTrue(generatedReport.contains("Message Hash:"));
        assertTrue(generatedReport.contains("Recipient:"));
        assertTrue(generatedReport.contains("Message Text:"));
    }

    @Test
    public void testCheckMessageID_viaConstructor() {
        Message msg = new Message("+27834557896", "Hello World", 0);
        assertNotNull(msg);
    }
}