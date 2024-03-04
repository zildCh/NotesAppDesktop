package notes.repository;
import notes.DAO.CategoryDAO;
import notes.notesappdesktop.Category;
import notes.notesappdesktop.Now;
import java.util.List;

public class CategoryRepository {
    private final CategoryDAO categoryDAO;

    //private Now now;
    public CategoryRepository() {
        CategoryDAO categoryDAO = new CategoryDAO();
        this.categoryDAO = categoryDAO;
    }

    public void createCategory(Category category) {
        categoryDAO.addCategory(category);
    }

    public void updateCategory(Category category) {
        categoryDAO.updateCategory(category);
    }

    public void deleteCategory(int categoryId) {
        categoryDAO.deleteCategory(categoryId);
    }

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public String getCategoryById(int categoryId) {
        return categoryDAO.getCategoryById(categoryId);
    }

    public int getCategoryIdByString(String categoryName) {
        return categoryDAO.getCategoryIdByString(categoryName);
    }
    // Другие методы, если необходимо
}