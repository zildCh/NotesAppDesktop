package notes.notesappdesktop;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NoteTileController {
    @FXML
    private Label titleLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label categoryLabel;

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setContent(String content) {
        contentLabel.setText(content);
    }

    public void setDate(String date) {
        dateLabel.setText(date);
    }

    public void setCategory(String category) {
        categoryLabel.setText(category);
    }


}
