package notes.notesappdesktop;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import notes.DAO.CategoryDAO;
import notes.DAO.NoteDAO;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загружаем FXML файл для экрана отображения заметок
        Parent showNotesRoot = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene showNotesScene = new Scene(showNotesRoot);

        // Устанавливаем заголовок окна
        primaryStage.setTitle("Notes App");

        // Устанавливаем начальную сцену (экран заметок)
        primaryStage.setScene(showNotesScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
       launch(args);
   }
}
