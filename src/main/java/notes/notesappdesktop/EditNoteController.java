package notes.notesappdesktop;

import javafx.collections.FXCollections;
import javafx.stage.Stage;
import notes.httpRequests.HttpRequest;
import notes.models.Category;
import notes.models.Note;
import notes.models.Now;
import notes.models.User;
import notes.repository.CategoryRepository;
import notes.repository.NoteRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.stream.Collectors;

public class EditNoteController{
    User user = new User();
    private final Now now = new Now.Base();
    @FXML
    private TextField titleField;
    @FXML
    private TextArea contentField;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;


    CategoryRepository categoryRepo = new CategoryRepository();
    NoteRepository noteRepo = new NoteRepository();
    HttpRequest httpRequest = new HttpRequest();

    private List<Category> categories; // Список категорий
    private List<Note> notes;
    private ShowNotesController showNotesController;

    private long note_id;


    public void setTitle(String title) {
        titleField.setText(title);
    }

    public void setContent(String content) {
        contentField.setText(content);
    }

    public void setCategoryChoiceBox(List<String> categoryNames, String currentCategory ) {
        categoryChoiceBox.setItems(FXCollections.observableArrayList(categoryNames));
        categoryChoiceBox.setValue(currentCategory);
    }
    public void setNoteAndCategory(long note_id, int category_id) {
        this.note_id = note_id;
        //this.category_id = category_id;
        String currentCategory = categoryRepo.getCategoryById(category_id);
        categories = categoryRepo.getAllCategories();
        List<String> categoryNames = categories.stream()
                .map(Category::getCategory)
                .collect(Collectors.toList());
        //получаем note
        Note note = noteRepo.getNoteById(note_id);
        setTitle(note.getTitle());
        setContent(note.getContent());
        setCategoryChoiceBox(FXCollections.observableArrayList(categoryNames), currentCategory);
    }

    public void setUser(User user){
        this.user = user;
    }

    @FXML
    private void saveNote() {
        System.out.println(categoryChoiceBox.getValue());
        String title = titleField.getText();
        String category = categoryChoiceBox.getValue();
        String content = contentField.getText();
        int category_id = categoryRepo.getCategoryIdByString(category);

        long date = now.timeInMillis();

        Note note2 = new Note(note_id,category_id, title, content, date);

        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();

        boolean upd = httpRequest.updateNote(user, note2);
        if (upd) {
            noteRepo.updateNote(note2);
        }
        else System.out.println("Error");


        if (showNotesController != null) {
            showNotesController.refreshNoteList();
        }
    }

    public void setShowNotesController(ShowNotesController controller) {
        this.showNotesController = controller;
    }
}
