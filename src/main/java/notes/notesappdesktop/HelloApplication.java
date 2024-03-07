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
        // Загружаем FXML файл для экрана добавления заметки
        // Parent addNoteRoot = FXMLLoader.load(getClass().getResource("addNote.fxml"));
       // Scene addNoteScene = new Scene(addNoteRoot);

        // Загружаем FXML файл для экрана отображения заметок
        Parent showNotesRoot = FXMLLoader.load(getClass().getResource("showNotes2.fxml"));
        Scene showNotesScene = new Scene(showNotesRoot);

/*        Parent showNotesRoot = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene showNotesScene = new Scene(showNotesRoot);*/

        // Устанавливаем заголовок окна
        primaryStage.setTitle("Notes App");

        // Устанавливаем начальную сцену (экран заметок)
        primaryStage.setScene(showNotesScene);
        primaryStage.show();




        // Переключаемся на экран отображения заметок при необходимости
        // Пример: primaryStage.setScene(showNotesScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
/*public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //CategoryDAO categoryDAO = new CategoryDAO();
        //NoteDAO noteDAO = new NoteDAO();

        //List<Note> noteList = noteDAO.getAllNotes();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}*/
