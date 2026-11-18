package com.poe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StoredMessagesTest {

    // Test data from POE Part 3
    // We add 5 messages exactly like the POE asks us to test
    public StoredMessages setupData() {
        StoredMessages store = new StoredMessages();
        store.clearAll();
        store.addMessage("Did you get the cake?", "+27718693002", "00:0:Did You Get The Cake?", "1234567890", "Mahlodi", "Sent");
        store.addMessage("Where are you? You are late! I have asked you to be on time.", "+27718693002", "01:1:Where Are You?", "1234567891", "Mahlodi", "Stored");
        store.addMessage("Yup, I have arrived to collect the parcel.", "+27834557896", "02:2:Yup I Have", "1234567892", "Mahlodi", "Stored");
        store.addMessage("I will be there soon, the meeting is at 10am.", "+27834557896", "03:3:I Will Be", "1234567893", "Mahlodi", "Sent");
        store.addMessage("I need to get some airtime, please send me R50.", "+27718693002", "04:4:I Need To", "1234567894", "Mahlodi", "Disregard");
        return store;
    }

    @Test
    public void testDisplaySenderAndRecipient() {
        StoredMessages store = setupData();
        String result = store.displaySenderAndRecipient();
        assertTrue(result.contains("+27718693002"));
        assertTrue(result.contains("+27834557896"));
    }

    @Test
    public void testDisplayLongestMessage() {
        StoredMessages store = setupData();
        assertEquals("Where are you? You are late! I have asked you to be on time.", store.displayLongestMessage());
    }

    @Test
    public void testSearchByMessageID() {
        StoredMessages store = setupData();
        assertEquals("Yup, I have arrived to collect the parcel.", store.searchByMessageID("1234567892"));
    }

    @Test
    public void testSearchByRecipient() {
        StoredMessages store = setupData();
        String result = store.searchByRecipient("+27834557896");
        assertTrue(result.contains("Yup, I have arrived to collect the parcel."));
        assertTrue(result.contains("I will be there soon, the meeting is at 10am."));
    }

    @Test
    public void testDeleteByHash() {
        StoredMessages store = setupData();
        String result = store.deleteByHash("04:4:I Need To");
        assertTrue(result.contains("successfully deleted"));
        assertEquals("Message hash not found.", store.deleteByHash("04:4:I Need To"));
    }

    @Test
    public void testDisplayReport() {
        StoredMessages store = setupData();
        String report = store.displayReport();
        assertTrue(report.contains("00:0:Did You Get The Cake?"));
        assertTrue(report.contains("+27718693002"));
        assertTrue(report.contains("Did you get the cake?"));
    }
}
