package com.mycompany.chatapppart1;

import com.mycompany.chatapp.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    Message msg1, msg2, msg3, msg4, msg5;

    @BeforeEach
    public void setUp() {
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
    public void testCreateMessageHash() {
        Message msg = new Message();
        msg.setMessageID("0012345678");
        msg.setMessageText("Hi Mike, can you join us for dinner tonight?");
        msg.setTotalMessages(0);
        String hash = msg.createMessageHash();
        assertEquals("00:0:HITONIGHT", hash);
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
    public void testDisplayLongestMessage_returnsCorrectMessage() {
        Message.addToStoredMessages("Did you get the cake?");
        Message.addToStoredMessages("Where are you? You are late! I have asked you to be on time.");
        Message.addToStoredMessages("Hows it going?");
        Message.addToStoredMessages("It is dinner time!");
        Message.addToStoredMessages("Ok, I am leaving without you.");
        String longest = msg1.displayLongestMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchByMessageID_returnsCorrectMessage() {
        msg4.sentMessage(1);
        String result = msg4.searchByMessageID("0838884567");
        assertEquals("It is dinner time!", result);
    }

    @Test
    public void testSearchByRecipient_returnsAllMatchingMessages() {
        msg1.sentMessage(1);
        msg2.sentMessage(1);
        msg3.sentMessage(1);
        msg4.sentMessage(1);
        msg5.sentMessage(1);
        String result = msg2.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteByHash_removesCorrectMessage() {
        msg2.sentMessage(1);
        String hash = msg2.getMessageHash();
        String result = msg2.deleteByHash(hash);
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("successfully deleted"));
    }

    @Test
    public void testDisplayReport_containsRequiredFields() {
        msg1.sentMessage(1);
        String report = Message.printReport();
        assertTrue(report.contains(msg1.getMessageHash()));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
    }
}