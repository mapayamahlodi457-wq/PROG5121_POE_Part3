package com.poe;
import java.util.Scanner;
        // Main class - runs QuickChat app
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("First Name: ");
        String first = sc.nextLine();
        System.out.print("Last Name: ");
        String last = sc.nextLine();
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Cell (e.g. +27838968976): ");
        String cell = sc.nextLine();

        // Create User - your User needs 5 things
        User user = new User(first, last, username, password, cell);
        Login login = new Login(user);

        // Register - this will show if username/password/cell is correct
        String regResult = login.registerUser(username, password, cell);
        System.out.println(regResult);

        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
        String lu = sc.nextLine();
        System.out.print("Password: ");
        String lp = sc.nextLine();

        boolean isLoggedIn = login.loginUser(lu, lp);
        String loginMessage = login.returnLoginStatus(isLoggedIn);
        System.out.println(loginMessage);

        // Login before showing menu
        // If login failed, stop
        if (!isLoggedIn) {
            System.out.println("Login failed. Exiting.");
            sc.close();
            return;
        }

        // ===== PART 2 STARTS HERE =====
        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages would you like to send? ");
        int numMessages = Integer.parseInt(sc.nextLine());

        Message[] sentMessages = new Message[100];
        int messageCount = 0;
        int totalMessages = 0;
        StoredMessages store = new StoredMessages();
        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Display sender and recipient of stored messages");
            System.out.println("5) Display longest message");
            System.out.println("6) Search by Message ID");
            System.out.println("7) Search by Recipient");
            System.out.println("8) Delete by Message Hash");
            System.out.println("9) Display Report");
            System.out.println("10) Read stored messages from JSON");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                for (int i = 0; i < numMessages; i++) {
                    System.out.println("\nMessage " + (messageCount + 1));

                    String messageId = Message.generateMessageID();
                    System.out.println("Message ID: " + messageId);

                    // Recipient validation loop
                    String recipient;
                    while (true) {
                        System.out.print("Enter recipient (with + and max 10 chars after code, e.g. +27718693002): ");
                        recipient = sc.nextLine();
                        Message temp = new Message(messageId, messageCount, recipient, "");
                        String check = temp.checkRecipientCell();
                        System.out.println(check);
                        if (check.equals("Cell phone number successfully captured.") || check.equals("Cell phone number successfully captured") ) {
                            break;
                        }
                        if (recipient.startsWith("+") && recipient.length() <= 13) {
                            break;
                        }
                    }

                    // Message text loop
                    String text;
                    while (true) {
                        System.out.print("Enter message (max 250 chars): ");
                        text = sc.nextLine();
                        Message temp2 = new Message(messageId, messageCount, recipient, text);
                        String lenCheck = temp2.checkMessageLength();
                        if (lenCheck.startsWith("Message ready")) {
                            System.out.println(lenCheck);
                            break;
                        } else {
                            System.out.println(lenCheck);
                        }
                    }

                    Message msg = new Message(messageId, messageCount, recipient, text);

                    // Hash
                    String hash = msg.createMessageHash();
                    System.out.println("Message Hash: " + hash);

                    // Ask what to do
                    System.out.println("\nWhat would you like to do?");
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message");
                    System.out.print("Choose (1/2/3): ");
                    int sendChoice = Integer.parseInt(sc.nextLine());

                    String sentResult = msg.SentMessage(sendChoice);
                    System.out.println(sentResult);

                    if (sendChoice == 1) {
                        System.out.println(msg.getMessageDetails());
                        sentMessages[messageCount] = msg;
                        messageCount++;
                        totalMessages++;
                        store.addMessage(text, recipient, hash, messageId, username, "Sent");
                        System.out.println("Total messages sent: " + msg.returnTotalMessages(totalMessages));
                    } else if (sendChoice == 3) {
                        msg.storeMessage();
                        sentMessages[messageCount] = msg;
                        messageCount++;
                        store.addMessage(text, recipient, hash, messageId, username, "Stored");
                    } else {
                        // disregard
                        store.addMessage(text, recipient, hash, messageId, username, "Disregard");
                    }
                }
            } else if (choice.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (choice.equals("4")) {
                System.out.println(store.displaySenderAndRecipient());
            } else if (choice.equals("5")) {
                System.out.println("Longest message: " + store.displayLongestMessage());
            } else if (choice.equals("6")) {
                System.out.print("Enter Message ID: ");
                String searchId = sc.nextLine();
                System.out.println(store.searchByMessageID(searchId));
            } else if (choice.equals("7")) {
                System.out.print("Enter Recipient: ");
                String searchRec = sc.nextLine();
                System.out.println(store.searchByRecipient(searchRec));
            } else if (choice.equals("8")) {
                System.out.print("Enter Message Hash: ");
                String delHash = sc.nextLine();
                System.out.println(store.deleteByHash(delHash));
            } else if (choice.equals("9")) {
                System.out.println(store.displayReport());
            } else if (choice.equals("10")) {
                System.out.println(store.readStoredMessagesFromJSON());
            } else if (choice.equals("3")) {
                System.out.println("Quitting. Total messages sent: " + totalMessages);
                running = false;
            } else {
                System.out.println("Invalid option.");
            }
        }

        sc.close();
    }
}