module com.eavmusic.eavmusic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.eavmusic.eavmusic to javafx.fxml;
    exports com.eavmusic.eavmusic;
    exports com.eavmusic.eavmusic.Controller;
    opens com.eavmusic.eavmusic.Controller to javafx.fxml;
    exports com.eavmusic.eavmusic.Model;
    opens com.eavmusic.eavmusic.Model to javafx.fxml;
}