package notes.models;
import com.google.gson.annotations.SerializedName;
import notes.models.Category;
import notes.models.Note;

import java.util.List;

public class UserCategoryLink {
    private Category category;
    @SerializedName("noteList")
    private List<Note> notes;

    public Category getCategory() {
        return category;
    }

    public List<Note> getNotes() {
        return notes;
    }

}
