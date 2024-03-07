package notes.notesappdesktop;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;

import java.io.*;
import java.nio.channels.NetworkChannel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import notes.httpRequests.HttpRequest;

public class RegistrationController {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label statusLabel;

    @FXML
    private void registrationButton() {
        HttpRequest httpRequest = new HttpRequest();
        String username = loginField.getText();
        String password = passwordField.getText();
        boolean registration = httpRequest.registration(username, password);

        if (registration){
          User user = httpRequest.authorization(username, password);
          if (user != null) {
              try {
                  Stage primaryStage = new Stage();
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("showNotes2.fxml"));
                  Parent showNotesRoot = loader.load();
                  showNotesController controller = loader.getController();
                  controller.setUser(user);

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
              statusLabel.setText("Registration done, but login error");
          }

        }
        else {
            statusLabel.setText("Registration error, please try again");
        }
        // Здесь нужно проверить введенные данные и выполнить регистрацию
    }
    @FXML
    private void backButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
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
