package com.mycompany.chatapppart1;
import com.mycompany.chatapp.Message;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lutho
 */
public class MessageTest {
    
    // Test 1: Message length valid - under 250 chars
    @Test
    public void testMessageLengthValid() {
        Message msg = new Message();
        msg.setMessageText("Hi Mike, can you join us for dinner tonight?");
        String result = msg.checkMessageLength();
        assertEquals("Message ready to send.", result);
    }

    // Test 2: Message length invalid - over 250 chars
    @Test
    public void testMessageLengthInvalid() {
        Message msg = new Message();
        // Create a 260 char string = 10 chars over limit
        String longMsg = "A".repeat(260);
        msg.setMessageText(longMsg);
        String result = msg.checkMessageLength();
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", result);
    }

    // Test 3: Recipient cell valid - POE Test message 1
    @Test
    public void testRecipientCellValid() {
        Message msg = new Message();
        msg.setRecipientCell("+27718693002");
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }

    // Test 4: Recipient cell invalid - POE Test message 2
    @Test
    public void testRecipientCellInvalid() {
        Message msg = new Message();
        msg.setRecipientCell("08575975889"); // No international code
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    // Test 5: Message hash - POE Test message 1
    @Test
    public void testCreateMessageHash() {
        Message msg = new Message();
        msg.setMessageID("0012345678"); // Example ID from your method
        msg.setMessageText("Hi Mike, can you join us for dinner tonight?");
        msg.setTotalMessages(0); // First message
        String hash = msg.createMessageHash();
        assertEquals("00:0:HITONIGHT", hash); // Format: first 2 of ID : num : HI+TONIGHT
    }

    // Test 6: Sent message - Send option
    @Test
    public void testSentMessage_Send() {
        Message msg = new Message();
        String result = msg.sentMessage(1); // 1 = Send option
        assertEquals("Message successfully sent.", result);
    }

    // Test 7: Sent message - Discard option
    @Test
    public void testSentMessage_Discard() {
        Message msg = new Message();
        String result = msg.sentMessage(2); // 2 = Disregard
        assertEquals("Press 0 to delete the message.", result);
    }

    // Test 8: Sent message - Store option
    @Test
    public void testSentMessage_Store() {
        Message msg = new Message();
        String result = msg.sentMessage(3); // 3 = Store
        assertEquals("Message successfully stored.", result);
    }
}