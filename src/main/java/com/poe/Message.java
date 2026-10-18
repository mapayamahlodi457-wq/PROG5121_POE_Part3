package com.poe;
import java.util.Random;
import java.io.FileWriter;

public class Message {
    private String messageId;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    public Message(String messageId, int messageNumber, String recipient, String messageText) {
        this.messageId = messageId;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
    }

    public static String generateMessageID() {
        Random rand = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id = id + rand.nextInt(10);
        }
        return id;
    }

    public String checkRecipientCell() {
        if (recipient == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        if (recipient.startsWith("+") && recipient.length() <= 13) {
            String afterPlus = recipient.substring(1);
            boolean allDigits = true;
            for (int i = 0; i < afterPlus.length(); i++) {
                if (!Character.isDigit(afterPlus.charAt(i))) {
                    allDigits = false;
                    break;
                }
            }
            if (allDigits && afterPlus.length() >= 10) {
                return "Cell phone number successfully captured.";
            }
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce size.";
        }
    }

    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        lastWord = lastWord.replaceAll("[^A-Z]", "");
        String idFirstTwo = messageId.substring(0, 2);
        this.messageHash = idFirstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
        return this.messageHash;
    }

    public String SentMessage(int choice) {
        if (choice == 1) {
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid choice.";
        }
    }

    public void storeMessage() {
        try {
            String filePath = "stored_messages.json";
            FileWriter file = new FileWriter(filePath, true);
            file.write("{\"messageId\":\"" + messageId +
                    "\",\"messageNumber\":" + messageNumber +
                    ",\"messageHash\":\"" + messageHash +
                    "\",\"recipient\":\"" + recipient +
                    "\",\"messageText\":\"" + messageText.replace("\"", "'") + "\"}\n");
            file.close();
        } catch (Exception e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public String getMessageDetails() {
        return "MessageID: " + messageId + "\nMessageHash: " + messageHash + "\nRecipient: " + recipient + "\nMessage: " + messageText;
    }

    public int returnTotalMessages(int total) {
        return total;
    }
}