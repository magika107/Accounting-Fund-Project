package mft.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import mft.controller.exceptions.UserNotFoundException;
import mft.model.entity.Person;
import mft.model.ropository.PersonRepository;

@Log4j2
public class LoginController {

    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Button loginBtn;

    @FXML
    public void initialize() {
        loginBtn.setOnAction(event -> {
            try (PersonRepository personRepository = new PersonRepository()) {
                Person person = personRepository.login(
                        usernameTxt.getText(),
                        passwordTxt.getText()
                );

                loginBtn.getScene().getWindow().hide();

                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/main.fxml")));
                stage.setScene(scene);
                stage.setTitle("Person Information");
                stage.show();

            } catch (UserNotFoundException e) {
                showAlert("ورود ناموفق", "نام کاربری یا رمز عبور اشتباه است !!!");
            } catch (Exception e) {
                showAlert("خطا", "مشکلی در ورود به سیستم رخ داده است !!!!");
                log.error("Login failed due to unexpected error", e);
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}