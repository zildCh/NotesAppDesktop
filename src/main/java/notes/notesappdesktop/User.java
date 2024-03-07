package notes.notesappdesktop;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class User {
    private String username;
    private String password;
    private int id;
    @SerializedName("categoryDaoList")
    private List<Category> categories;

    public User(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public User(){
    }
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
