package notes.notesappdesktop;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Category {

    private int category_id;
    private String category;

    @SerializedName("noteDaoList")
    private List<Note> notes;

    public Category() {
        category_id = 0;
        category = "";
    }
    public Category(int category_id, String category) {
        this.category_id = category_id;
        this.category = category;
    }
    public int getCategory_id() {return category_id;}
    public void setCategory_id(int category_id) {this.category_id = category_id;}
    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}

    public List<Note> getNotes() {
        return notes;
    }
}
