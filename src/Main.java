import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load ChatGUI.fxml instead of MainView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatGUI.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Chat App");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
