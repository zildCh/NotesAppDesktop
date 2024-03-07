package notes.models;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("nickname")
    private String username;
    private String password;
    private int id;
    @SerializedName("userCategoryLinkList")
    private List<UserCategoryLink> userCategoryLinks;

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

    // Сеттер для username
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String username) {
        this.username = username;
    }
    public List<UserCategoryLink> getUserCategoryLinks() {
        return userCategoryLinks;
    }

}
