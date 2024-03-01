package notes.notesappdesktop;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import notes.DAO.NoteDAO;
import notes.DAO.CategoryDAO;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField titleText;
    @FXML
    protected void onHelloButtonClick() {
        //Category category = new Category(1,"sport");
       // Note note = new Note(1,1,"Skiing", "Go to Ski", System.currentTimeMillis());
        CategoryDAO categoryDAO = new CategoryDAO();
        //NoteDAO noteDAO = new NoteDAO();
        //categoryDAO.deleteCategory(1);
        //categoryDAO.addCategory(category);
        //noteDAO.addNote(note);
        welcomeText.setText("Welcome to JavaFX Application!");
        List<Category> categories = new ArrayList<>();
        categories = categoryDAO.getAllCategories();
        Category category = categories.get(0);
        titleText.setText(category.getCategory());
    }
}