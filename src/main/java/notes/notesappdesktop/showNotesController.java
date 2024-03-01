package notes.notesappdesktop;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import notes.repository.CategoryRepository;
import notes.repository.NoteRepository;
import java.util.Date;
import java.text.SimpleDateFormat;

import java.io.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
import javafx.fxml.FXMLLoader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.event.ActionEvent;
public class showNotesController implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private void handleAddButton() {  //обработчик нажатия на кнопку добавления заметки
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addNote.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            AddNoteController addNoteController = loader.getController();
            addNoteController.setShowNotesController(this);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Метод для добавления заметок в список плиток
    public void addNoteTile(Note note) {
        // Получаем название категории по id_category
        String categoryName = getCategoryNameById(note.getCategory());
        // Преобразуем дату из миллисекунд в строку
        String formattedDate = convertMillisToDate(note.getDate());
        // Создайте новый экземпляр макета FXML для заметки
        FXMLLoader loader = new FXMLLoader(getClass().getResource("noteTile2.fxml"));
        try {
            Node noteTile = loader.load();
            NoteTileController controller = loader.getController();
            controller.setTitle(note.getTitle());
            controller.setContent(note.getContent());
            controller.setCategory(categoryName);
            controller.setDate(formattedDate);
            vbox.getChildren().add(noteTile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshNoteList();
       /* handleSaveNoteEvent(SaveNoteEvent.SAVE_NOTE, event -> {
            // Выполните необходимые действия при сохранении заметки
            refreshNoteList(); // Например, обновление списка заметок
        });*/
       // CategoryRepository categoryRepo = new CategoryRepository();
       // List<Category> categories = new ArrayList<>();
       // Category category = new Category(2, "Family");
        // categoryRepo.createCategory(category);
         // Note note = new Note(0L,1, "Skiing", "I'm going skiing on Wednesday", System.currentTimeMillis());
       // Note note = new Note(0L,2, "Evening with family", "We're all planning to watch a new movie tonight.", System.currentTimeMillis());

        //noteRepo.createNote(note);
    }

    public void refreshNoteList() {
        NoteRepository noteRepo = new NoteRepository();
        List<Note> notes = new ArrayList<>();
        notes = noteRepo.getAllNotes();
        //очищаем vbox
        vbox.getChildren().clear();
        for (Note note : notes) {
            addNoteTile(note);
            //noteRepo.deleteNote(note.getId());
        }

        // Код для обновления списка заметок
    }

    private String getCategoryNameById(int categoryId) {
        CategoryRepository categoryRepo = new CategoryRepository();
        Category category = categoryRepo.getCategoryById(categoryId);
        if (category != null) {
            return category.getCategory();
        } else {
            return ""; // Если категория не найдена, возвращаем пустую строку или другое значение по умолчанию
        }
    }
    private String convertMillisToDate(long millis) {
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}