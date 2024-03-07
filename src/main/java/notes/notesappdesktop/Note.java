package notes.notesappdesktop;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Note {
    private long id;
    @SerializedName("header")
    private String title;
    @SerializedName("note")
    private String content;
    private  long date;
    private int id_category;

    public Note() {
        id = 0L;
        title = "";
        content = "";
        date = 0;
        id_category = 0;
    }
    public Note(long id, int id_category,String title, String content, long date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.id_category = id_category;
    }
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getCategory() {
        return id_category;
    }

    public void setCategory(int id_category) {
        this.id_category = id_category;
    }



}

