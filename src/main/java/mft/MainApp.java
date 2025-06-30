package mft;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
            Scene scene = new Scene(loader.load());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();

            log.info("Application started successfully");

        } catch (Exception e) {
            log.error("Failed to start application", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
