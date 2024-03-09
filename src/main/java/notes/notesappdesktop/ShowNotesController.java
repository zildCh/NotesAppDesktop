package notes.notesappdesktop;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import notes.httpRequests.HttpRequest;
import notes.models.Category;
import notes.models.Note;
import notes.models.User;
import notes.repository.CategoryRepository;
import notes.repository.NoteRepository;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import java.io.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.fxml.FXMLLoader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShowNotesController implements Initializable {
    CategoryRepository categoryRepo = new CategoryRepository();
    NoteRepository noteRepo = new NoteRepository();
    HttpRequest httpRequest = new HttpRequest();
    User user = new User();
    @FXML
    private VBox vbox;
    @FXML
    private ChoiceBox<String> categoryFilterChoiceBox;
    @FXML
    private void logoutButton(){
        try {
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent loginRoot = loader.load();
            LoginController controller = loader.getController();
            Scene loginScene = new Scene(loginRoot);
            // Устанавливаем заголовок окна
            primaryStage.setTitle("Notes App");
            // Устанавливаем начальную сцену (экран заметок)
            primaryStage.setScene(loginScene);
            primaryStage.show();

            Stage stage = (Stage) vbox.getScene().getWindow();
            stage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateButton(){
        httpRequest.updateData(user.getId());
        refreshNoteList();

    }
    @FXML
    private Label loginLabel;
    @FXML
    private void handleAddButton() {  //обработчик нажатия на кнопку добавления заметки
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addNote.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            AddNoteController addNoteController = loader.getController();
            addNoteController.setShowNotesController(this);
            addNoteController.setUser(user);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUser(User user) {
        this.user = user;
    }
    // Метод для добавления заметок в список плиток


    public void addNoteTile(Note note) {
        // Преобразуем дату из миллисекунд в строку
        String formattedDate = convertMillisToDate(note.getDate());
        // Создайте новый экземпляр макета FXML для заметки
        FXMLLoader loader = new FXMLLoader(getClass().getResource("noteTile2.fxml"));
        try {
            Node noteTile = loader.load();
            NoteTileController controller = loader.getController();

            controller.setUser(user);
            controller.setId(note.getId());
            controller.setTitle(note.getTitle());
            controller.setContent(note.getContent());
            controller.setCategory_id(note.getCategory());
            controller.setCategory();
            controller.setDate(formattedDate);
            vbox.getChildren().add(noteTile);
            controller.setShowNotesController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {};

    public void Init() {
        List<String> allCategoryNames = categoryRepo.getAllCategories().stream()
                .map(Category::getCategory)
                .collect(Collectors.toList());

        categoryFilterChoiceBox.getItems().add("All"); // Добавляем "All" в начало списка
        categoryFilterChoiceBox.getItems().addAll(allCategoryNames);
        categoryFilterChoiceBox.getSelectionModel().selectFirst();
        refreshNoteList();
        categoryFilterChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { // Установка выбранной категории
                refreshNoteList(); // Вызов метода для обновления списка заметок
            }


        });

    }
    public void setUserLabel(User user){
        loginLabel.setText(user.getUsername());
    }

    public void refreshNoteList() {
        String selectedCategory = categoryFilterChoiceBox.getValue();
        List<Note> notes = new ArrayList<>();
        notes = noteRepo.getAllNotes();
        //очищаем vbox
        vbox.getChildren().clear();
        //System.out.println(selectedCategory);
        boolean filterByCategory = !selectedCategory.equals("All");
        List<Note> filteredNotes = filterByCategory ?
                notes.stream()
                        .filter(note -> note.getCategory()==(categoryRepo.getCategoryIdByString(selectedCategory)))
                        .collect(Collectors.toList()) :
                notes;
        for (Note note : filteredNotes) {
            addNoteTile(note);
        }

        // Код для обновления списка заметок
    }

    private String convertMillisToDate(long millis) {
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}