module notes.notesappdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    opens notes.notesappdesktop to javafx.fxml;
    exports notes.notesappdesktop;
    exports notes.DAO;
    opens notes.DAO to javafx.fxml;
}