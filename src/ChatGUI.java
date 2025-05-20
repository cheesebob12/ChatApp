package chat;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.SocketException;

public class ChatGUI {

    @FXML private TextField usernameField;
    @FXML private TextField messageField;
    @FXML private TextArea chatArea;
    @FXML private Button sendButton;
    @FXML private Button disconnectButton;

    private ChatClient client;
    private Thread listenerThread;

    public void initialize() {
        messageField.setDisable(true);
        sendButton.setDisable(true);
        disconnectButton.setDisable(true);
        Platform.runLater(() -> usernameField.requestFocus());
    }

    @FXML
    public void sendButtonPressed() {
        String username = usernameField.getText().trim();
        String message = messageField.getText().trim();

        if (username.isEmpty() || message.isEmpty()) {
            chatArea.appendText("Username and message cannot be empty.\n");
            return;
        }

        if (client == null) {
            try {
                client = new ChatClient(12346, "localhost", username);
                chatArea.appendText("Connected as " + username + ".\n");

                listenerThread = new Thread(() -> {
                    try {
                        while (true) {
                            String incoming = client.getMsg();
                            if (incoming == null) break;
                            Platform.runLater(() -> chatArea.appendText(incoming + "\n"));
                        }
                    } catch (SocketException se) {
                        Platform.runLater(() -> chatArea.appendText("Disconnected.\n"));
                    } catch (IOException e) {
                        Platform.runLater(() -> chatArea.appendText("Connection error.\n"));
                    } finally {
                        disconnect(); // Auto-cleanup on listener exit
                    }
                });
                listenerThread.start();

                messageField.setDisable(false);
                sendButton.setDisable(false);
                disconnectButton.setDisable(false);
                usernameField.setDisable(true);

            } catch (Exception e) {
                chatArea.appendText("Failed to connect to server.\n");
                return;
            }
        }

        client.sendMsg(message);
        messageField.clear();
    }

    @FXML
    public void disconnect() {
        if (client != null) {
            try {
                client.sendMsg("*** " + client.getUser() + " has disconnected. ***");
            } catch (Exception ignored) {}

            try {
                if (listenerThread != null && listenerThread.isAlive()) {
                    listenerThread.interrupt();
                }
                client.sendMsg("DISCONNECT"); // Optional command for server-side logic
                client = null;
                chatArea.appendText("You have disconnected.\n");
            } catch (Exception e) {
                chatArea.appendText("Error during disconnect.\n");
            }
        }

        // Reset UI
        messageField.setDisable(true);
        sendButton.setDisable(true);
        disconnectButton.setDisable(true);
        usernameField.setDisable(false);
    }
}
