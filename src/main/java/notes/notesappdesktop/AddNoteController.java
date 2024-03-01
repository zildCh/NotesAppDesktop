package notes.notesappdesktop;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import notes.repository.CategoryRepository;
import notes.repository.NoteRepository;
import java.util.stream.Collectors;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;




public class AddNoteController implements Initializable {
    CategoryRepository categoryRepo = new CategoryRepository();
    NoteRepository noteRepo = new NoteRepository();
    private showNotesController showNotesController;

    @FXML
    private TextField titleField;
    @FXML
    private TextArea contentField;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    private List<Category> categories; // Список категорий


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        categories = categoryRepo.getAllCategories();
        List<String> categoryNames = categories.stream()
                .map(Category::getCategory)
                .collect(Collectors.toList());
        categoryChoiceBox.setItems(FXCollections.observableArrayList(categoryNames));
    }

    @FXML
    private void addNote(ActionEvent event) {
        String title = titleField.getText();
        String category = categoryChoiceBox.getValue();
        String content = contentField.getText();
        int category_id = categoryRepo.getCategoryIdByString(category);
        Note note = new Note(0L,category_id, title, content, 0L);
        noteRepo.createNote(note);

        //ЗАкрываем окно
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
        if (showNotesController != null) {
            showNotesController.refreshNoteList();
        }
    }
    public void setShowNotesController(showNotesController controller) {
        this.showNotesController = controller;
    }
    // Метод для установки списка категорий
    //public void setCategories(List<String> categories) {
       // this.categories = categories;
   // }
}