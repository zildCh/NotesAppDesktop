package notes.notesappdesktop;

public class Note {
    private long id;
    private String title;
    private String content;
    private  long date;
    private int id_category;

    public Note() {
        id = 0;
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

    private String htmlParser(String text) {
        String[] textArr = text.split("&");
        for (String x : textArr) {
            try {
                x = x.split(";")[0];
                switch (x) {
                    case "amp":
                        text = text.replace("&amp;", "&");
                        break;
                    case "quot":
                        text = text.replace("&quot;", "\"");
                        break;
                    case "lt":
                        text = text.replace("&lt;", "<");
                        break;
                    case "gt":
                        text = text.replace("&gt;", ">");
                        break;
                }
            } catch (Exception e) {
                //
            }
        }

        if (text.contains("\\\'")){
            text = text.replace("\\\'", "\'");
        }

        if (text.contains("\\\\")){
            text = text.replace("\\\\", "\\");
        }

        return text;
    }

}

