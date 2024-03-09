package notes.notesappdesktop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import notes.httpRequests.HttpRequest;
import notes.models.Category;
import notes.models.User;
import notes.repository.CategoryRepository;
import notes.repository.NoteRepository;
import java.io.*;
import java.util.List;

public class NoteTileController {
    User user = new User();
    HttpRequest httpRequest = new HttpRequest();
    CategoryRepository categoryRepo = new CategoryRepository();
    NoteRepository noteRepo = new NoteRepository();
    private List<Category> categories; // Список категорий
    private ShowNotesController showNotesController;

    @FXML
    private Label titleLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label categoryLabel;
    private long note_id;
    private int category_id;
    public void setId (long note_id) {this.note_id = note_id;};

    public void setCategory_id (int category_id) {this.category_id = category_id;};

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setContent(String content) {
        contentLabel.setText(content);
    }

    public void setDate(String date) {
        dateLabel.setText(date);
    }

    public void setCategory() {
        categoryLabel.setText(categoryRepo.getCategoryById(category_id));
    }

    public void setUser(User user){
        this.user = user;
    }

    @FXML
    private void handleDeleteImageClick() {
        // удаляем заметку
       if (httpRequest.deleteNote(user, category_id, note_id)) {
           noteRepo.deleteNote(note_id);
       }
       else { System.out.println("Error: no server connection");}

        // обновляем список
        if (showNotesController != null) {
           showNotesController.refreshNoteList();
        }
    }
    @FXML
    private void handleEditImageClick() {
        // Обрабатываем нажатие на кнопку edit
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editNote.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            EditNoteController editNoteController = loader.getController();
            editNoteController.setShowNotesController(this.showNotesController);
            editNoteController.setUser(user);
            stage.show();
            editNoteController.setNoteAndCategory(note_id, category_id);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setShowNotesController(ShowNotesController controller) {
        this.showNotesController = controller;
    }
}
