import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {
    @FXML private TextField usernameField;
    @FXML private TextField messageField;
    @FXML private TextArea chatArea;

    public void onSendClick() {
        String msg = usernameField.getText() + ": " + messageField.getText();
        chatArea.appendText(msg + "\n");
        messageField.clear();
    }
}
