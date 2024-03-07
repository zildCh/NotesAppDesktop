package notes.notesappdesktop;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import notes.httpRequests.HttpRequest;
import notes.models.User;

import java.io.*;

public class LoginController {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label statusLabel;
    @FXML
    private void loginButton() {
        HttpRequest httpRequest = new HttpRequest();
        String username = loginField.getText();
        String password = passwordField.getText();

        User user = httpRequest.authorization(username, password);

        if (user != null) {
            try {
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("showNotes2.fxml"));
                Parent showNotesRoot = loader.load();
                showNotesController controller = loader.getController();
                controller.setUser(user);
                controller.setUserLabel(user);
                controller.Init();

                Scene showNotesScene = new Scene(showNotesRoot);

                // Устанавливаем заголовок окна
                primaryStage.setTitle("Notes App");
                // Устанавливаем начальную сцену (экран заметок)
                primaryStage.setScene(showNotesScene);
                primaryStage.show();

                Stage stage = (Stage) loginField.getScene().getWindow();
                stage.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            statusLabel.setText("Login error");
        }

        // Здесь нужно проверить введенные данные и выполнить логин

    }
    @FXML
    private void registrationButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registration.fxml"));
            Parent root = loader.load();
            // Получаем сцену
            Scene scene = loginField.getScene();
            // Получаем корневой узел из сцены
            Parent root2 = scene.getRoot();
            // Устанавливаем новый корневой узел
            scene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
