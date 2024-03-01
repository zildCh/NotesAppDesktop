package notes.notesappdesktop;

public interface Now {
    long timeInMillis();

    class Base implements Now {
        @Override
        public long timeInMillis() {
            return System.currentTimeMillis();
        }
    }
}