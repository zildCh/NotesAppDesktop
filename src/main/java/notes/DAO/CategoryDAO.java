package notes.DAO;
import notes.notesappdesktop.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private Connection connection;
    // Конструктор для инициализации подключения к базе данных
    public CategoryDAO() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection("jdbc:derby:notesDB;create=true");
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "CATEGORY", null);
            if (!resultSet.next()) {
                // Таблица note не существует, база данных только что создана
                // Создаем таблицы
                createTables();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void createTables() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE category (category_id BIGINT PRIMARY KEY, category VARCHAR(255))");
            // Добавляем создание других таблиц, если это необходимо, например, таблицы категорий
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addCategory(Category category) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO category (category_id, category) VALUES (?,?)");
            preparedStatement.setInt(1, category.getCategory_id());
            preparedStatement.setString(2, category.getCategory());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateCategory(Category category) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE category SET category = ? WHERE category_id = ?");
            preparedStatement.setString(1, category.getCategory());
            preparedStatement.setLong(2, category.getCategory_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteCategory(long categoryId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM category WHERE category_id = ?");
            preparedStatement.setLong(1, categoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM category");
            while (resultSet.next()) {
                int categoryId = resultSet.getInt("category_id");
                String category = resultSet.getString("category");
                Category categoryObj = new Category(categoryId, category);
                categories.add(categoryObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public String getCategoryById(int categoryId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM category WHERE category_id = ?");
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String category = resultSet.getString("category");
                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getCategoryIdByString(String categoryName) {
        int categoryId = -1; // Идентификатор категории, -1 если категория не найдена
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT category_id FROM category WHERE category = ?");
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                categoryId = resultSet.getInt("category_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryId;
    }
}
