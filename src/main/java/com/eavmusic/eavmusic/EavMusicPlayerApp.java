package com.eavmusic.eavmusic;

import com.eavmusic.eavmusic.Controller.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EavMusicPlayerApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EavMusicPlayerApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 640);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setPrimaryStage(stage);

        stage.setTitle("Eav Music Player");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}