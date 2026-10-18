package com.poe;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String result = msg.checkMessageLength();
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testMessageLengthFailure() {
        String longText = "";
        for (int i = 0; i < 260; i++) {
            longText = longText + "a";
        }
        Message msg = new Message("0012345678", 0, "+27718693002", longText);
        String result = msg.checkMessageLength();
        assertTrue(result.contains("exceeds 250"));
    }

    @Test
    public void testRecipientSuccess() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Hello");
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testRecipientFailure() {
        Message msg = new Message("0012345678", 0, "08575975889", "Hello");
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testMessageHash() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String hash = msg.createMessageHash();
        assertEquals("00:0:HITONIGHT", hash);
    }

    @Test
    public void testMessageIDLength() {
        String id = Message.generateMessageID();
        assertEquals(10, id.length());
    }

    @Test
    public void testSentMessageSend() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Test");
        String result = msg.SentMessage(1);
        assertEquals("Message successfully sent.", result);
    }

    @Test
    public void testSentMessageDisregard() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Test");
        String result = msg.SentMessage(2);
        assertEquals("Press 0 to delete the message.", result);
    }

    @Test
    public void testSentMessageStore() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Test");
        String result = msg.SentMessage(3);
        assertEquals("Message successfully stored.", result);
    }

    @Test
    public void testMessageHashLoop() {
        // Tests remaining hashes in a loop as required in brief
        String[] messages = {"Hi Mike, can you join us for dinner tonight?", "Hi Keegan, did you receive the payment?"};
        String[] ids = {"0012345678", "0012345679"};
        String[] expected = {"00:0:HITONIGHT", "00:1:HIPAYMENT"};

        for (int i = 0; i < messages.length; i++) {
            Message msg = new Message(ids[i], i, "+27718693002", messages[i]);
            String hash = msg.createMessageHash();
            assertEquals(expected[i], hash);
        }
    }

    @Test
    public void testTotalMessages() {
        Message msg = new Message("0012345678", 0, "+27718693002", "Test");
        int total = msg.returnTotalMessages(2);
        assertEquals(2, total);
    }
}
