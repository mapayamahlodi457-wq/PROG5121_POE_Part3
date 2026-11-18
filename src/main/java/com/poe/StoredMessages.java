package com.poe;

import java.util.ArrayList;

public class StoredMessages {

    // Our parallel arrays
    public static ArrayList<String> sentMessages = new ArrayList<>();
    public static ArrayList<String> disregardedMessages = new ArrayList<>();
    public static ArrayList<String> storedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();
    public static ArrayList<String> recipients = new ArrayList<>();
    public static ArrayList<String> senders = new ArrayList<>();
    public static ArrayList<String> allMessages = new ArrayList<>();

    public void addMessage(String message, String recipient, String hash, String id, String sender, String flag) {
        messageIDs.add(id);
        messageHashes.add(hash);
        recipients.add(recipient);
        senders.add(sender);
        allMessages.add(message);

        if (flag.equals("Sent")) {
            sentMessages.add(message);
        } else if (flag.equals("Disregard")) {
            disregardedMessages.add(message);
        } else if (flag.equals("Stored")) {
            storedMessages.add(message);
        }
    }

    // 1. Display sender and recipient of all stored messages
    public String displaySenderAndRecipient() {
        String result = "";
        for (int i = 0; i < storedMessages.size(); i++) {
            // Find this stored message in allMessages to get sender/recipient
            int index = allMessages.indexOf(storedMessages.get(i));
            result = result + "Sender: " + senders.get(index) + ", Recipient: " + recipients.get(index) + "\n";
        }
        return result;
    }

    // 2. Display longest message
    public String displayLongestMessage() {
        String longest = "";
        for (int i = 0; i < allMessages.size(); i++) {
            if (allMessages.get(i).length() > longest.length()) {
                longest = allMessages.get(i);
            }
        }
        return longest;
    }

    // 3. Search for message ID and return the message
    public String searchByMessageID(String id) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                return allMessages.get(i);
            }
        }
        return "Message ID not found.";
    }

    // 4. Search all messages for a recipient
    public String searchByRecipient(String recipient) {
        String result = "";
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(recipient)) {
                result = result + allMessages.get(i) + "\n";
            }
        }
        if (result.equals("")) {
            return "No messages found for this recipient.";
        }
        return result;
    }

    // 5. Delete using message hash
    public String deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                String deleted = allMessages.get(i);
                // Remove from all lists
                allMessages.remove(i);
                messageHashes.remove(i);
                messageIDs.remove(i);
                recipients.remove(i);
                senders.remove(i);
                sentMessages.remove(deleted);
                storedMessages.remove(deleted);
                disregardedMessages.remove(deleted);
                return "Message: \"" + deleted + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // 6. Display report
    public String displayReport() {
        String report = "";
        for (int i = 0; i < allMessages.size(); i++) {
            report = report + "Message Hash: " + messageHashes.get(i)
                    + "\nRecipient: " + recipients.get(i)
                    + "\nMessage: " + allMessages.get(i) + "\n\n";
        }
        return report;
    }
    // Read messages from JSON file into arrays
    // Source for JSON reading: https://github.com/google/gson - Gson library
    // Adapted from Gson User Guide: https://github.com/google/gson/blob/main/UserGuide.md
    public String readStoredMessagesFromJSON() {
        try {
            com.google.gson.Gson gson = new com.google.gson.Gson();
            java.io.FileReader reader = new java.io.FileReader("stored_messages.json");
            com.google.gson.JsonArray array = gson.fromJson(reader, com.google.gson.JsonArray.class);
            reader.close();
            String result = "";
            for (int i = 0; i < array.size(); i++) {
                com.google.gson.JsonObject obj = array.get(i).getAsJsonObject();
                String msg = "";
                if (obj.has("message")) {
                    msg = obj.get("message").getAsString();
                } else if (obj.has("Message")) {
                    msg = obj.get("Message").getAsString();
                }
                if (!msg.equals("")) {
                    storedMessages.add(msg);
                    allMessages.add(msg);
                    result = result + msg + "\n";
                }
            }
            if (result.equals("")) {
                return "No stored messages found in JSON.";
            }
            return result;
        } catch (Exception e) {
            return "File not found.";
        }
    }
    public void clearAll() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        recipients.clear();
        senders.clear();
        allMessages.clear();
    }
}