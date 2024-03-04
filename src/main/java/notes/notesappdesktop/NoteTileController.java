package notes.notesappdesktop;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import notes.repository.CategoryRepository;
import notes.repository.NoteRepository;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class NoteTileController {

    CategoryRepository categoryRepo = new CategoryRepository();
    NoteRepository noteRepo = new NoteRepository();
    private List<Category> categories; // Список категорий
    private showNotesController showNotesController;

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

    //Note note = new Note(note_id,category_id, );
    @FXML
    private void handleDeleteImageClick() {
        // удаляем заметку
        noteRepo.deleteNote(note_id);
        //System.out.println(note_id);
        // обновляем список
        if (showNotesController != null) {
           showNotesController.refreshNoteList();
        }
    }
    @FXML
    private void handleEditImageClick() {
        // Ваш код обработки нажатия на картинку
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editNote.fxml"));
        try {
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            EditNoteController editNoteController = loader.getController();
            editNoteController.setShowNotesController(this.showNotesController);
            stage.show();
            //передаем id на всякий
           // editNoteController.setId2(note_id);
            //editNoteController.setCategoryId(category_id);

            editNoteController.setNoteAndCategory(note_id, category_id);
            //получаем категории

      /*      categoryChoiceBox.setItems(FXCollections.observableArrayList(categoryNames));
            categoryChoiceBox.setValue(currentCategory);
            System.out.println(note_id);
            System.out.println(categoryId);
            titleField.setText(note1.getTitle());
            contentField.setText(note1.getContent());*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setShowNotesController(showNotesController controller) {
        this.showNotesController = controller;
    }
}
