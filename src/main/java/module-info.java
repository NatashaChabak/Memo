module com.example.memo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.memo to javafx.fxml;
    exports com.example.memo;
    exports Memo to javafx.graphics;


}