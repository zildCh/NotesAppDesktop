package notes.DAO;
import notes.models.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    private Connection connection;
    public NoteDAO() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection("jdbc:derby:notesDB;create=true");
            // Проверка существования таблицы note
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "NOTE", null);
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
           /* Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE note");*/
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE note (id BIGINT PRIMARY KEY,category_id INT, title VARCHAR(255), content VARCHAR(1024), date BIGINT, FOREIGN KEY (category_id) REFERENCES category(category_id))");
            // Добавляем создание других таблиц, если это необходимо, например, таблицы категорий*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addNote(long id, Note note, long date) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO note (id, category_id, title, content, date) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, id);
            preparedStatement.setInt(2, note.getCategory());
            preparedStatement.setString(3, note.getTitle());
            preparedStatement.setString(4, note.getContent());
            preparedStatement.setLong(5, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateNote(Note note,long date) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement ("UPDATE note SET category_id = ?, title = ?, content = ?, date = ? WHERE id = ?");
            preparedStatement.setInt(1, note.getCategory());
            preparedStatement.setString(2, note.getTitle());
            preparedStatement.setString(3, note.getContent());
            preparedStatement.setLong(4, date);
            preparedStatement.setLong(5, note.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void deleteNote(long noteId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM note WHERE id = ?");
            preparedStatement.setLong(1, noteId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM note");
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                int categoryId = resultSet.getInt("category_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Long date = resultSet.getLong("date");
                Note note = new Note(id, categoryId, title, content, date);
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }


    public Note getNoteById(long noteId) {
        Note note = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM note WHERE id = ?");
            preparedStatement.setLong(1, noteId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                int categoryId = resultSet.getInt("category_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                long date = resultSet.getLong("date");
                note = new Note(id, categoryId, title, content, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return note;
    }
}


