package notes.notesappdesktop;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import notes.models.Category;
import notes.models.Note;
import notes.models.Now;
import notes.models.User;
import notes.repository.CategoryRepository;
import notes.repository.NoteRepository;
import java.util.stream.Collectors;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import notes.httpRequests.HttpRequest;



public class AddNoteController implements Initializable {
    User user = new User();
    private final Now now = new Now.Base();
    CategoryRepository categoryRepo = new CategoryRepository();
    NoteRepository noteRepo = new NoteRepository();
    private ShowNotesController showNotesController;

    @FXML
    private TextField titleField;
    @FXML
    private TextArea contentField;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    private List<Category> categories; // Список категорий

    public void setUser(User user){
    this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        categories = categoryRepo.getAllCategories();
        List<String> categoryNames = categories.stream()
                .map(Category::getCategory)
                .collect(Collectors.toList());
        categoryChoiceBox.setItems(FXCollections.observableArrayList(categoryNames));
        categoryChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void addNote(ActionEvent event) {
        HttpRequest httpRequest = new HttpRequest();
        String title = titleField.getText();
        String category = categoryChoiceBox.getValue();
        String content = contentField.getText();
        int category_id = categoryRepo.getCategoryIdByString(category);
        long date = now.timeInMillis();

        Note note = new Note(0L,category_id, title, content, date);
        long noteId = httpRequest.createNote(user, note);

        noteRepo.createNote(noteId, note);

        // noteRepo.createNote(note);
        //ЗАкрываем окно
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
        if (showNotesController != null) {
            showNotesController.refreshNoteList();
        }
    }
    public void setShowNotesController(ShowNotesController controller) {
        this.showNotesController = controller;
    }

}