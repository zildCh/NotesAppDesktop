package notes.repository;
import notes.notesappdesktop.Note;
import notes.DAO.NoteDAO;
import notes.notesappdesktop.Now;
import java.util.List;
public class NoteRepository {
    private final NoteDAO noteDAO;
    private final Now now = new Now.Base();
    public NoteRepository() {
        NoteDAO noteDAO = new NoteDAO();
        this.noteDAO = noteDAO;
    }

    public void createNote(Note note) {
        long id = now.timeInMillis();
        long date = now.timeInMillis();
        noteDAO.addNote(id, note, date);
    }

    public void updateNote(Note note) {
        noteDAO.updateNote(note);
    }

    public void deleteNote(Long noteId) {
        noteDAO.deleteNote(noteId);
    }

    public List<Note> getAllNotes() {
        return noteDAO.getAllNotes();
    }

    // Другие методы, если необходимо
}