package mft.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import mft.model.entity.Person;
import mft.model.service.PersonService;
import mft.controller.exceptions.PersonNotFoundException;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class PersonController implements Initializable {
    @FXML
    private TextField idTxt, nameTxt, familyTxt, phoneNumberTxt, userNameTxt, nameSearchTxt, familySearchTxt;
    @FXML
    private PasswordField passwordPas;
    @FXML
    private DatePicker birthDate;
    @FXML
    private Button saveBtn, editBtn, removeBtn, clearBtn;
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, Integer> idCol;
    @FXML
    private TableColumn<Person, String> nameCol, familyCol, usernameCol;

    private List<Person> personList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetForm();

        saveBtn.setOnMouseClicked(event -> {
            try {
                Person person = Person.builder()
                        .name(nameTxt.getText())
                        .family(familyTxt.getText())
                        .username(userNameTxt.getText())
                        .password(passwordPas.getText())
                        .birthDate(birthDate.getValue())
                        .phoneNumber(phoneNumberTxt.getText())
                        .build();
                PersonService.save(person);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Person created successfully", ButtonType.OK);
                alert.show();
                resetForm();
                log.info("Person created successfully: " + person);

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to create person", ButtonType.OK);
                alert.show();
                log.error("Person Creation Error: " + e.getMessage());
            }
        });

        editBtn.setOnAction(event -> {
            try {
                Person person = Person.builder()
                        .id(Integer.parseInt(idTxt.getText()))
                        .name(nameTxt.getText())
                        .family(familyTxt.getText())
                        .username(userNameTxt.getText())
                        .password(passwordPas.getText())
                        .birthDate(birthDate.getValue())
                        .phoneNumber(phoneNumberTxt.getText())
                        .build();
                PersonService.edit(person);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Person Edited Successfully", ButtonType.OK);
                alert.show();
                resetForm();
                log.info("Person Edited Successfully: " + person);

            } catch (PersonNotFoundException pnfe) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Person not found", ButtonType.OK);
                alert.show();
                log.warn("Person not found for editing: " + pnfe.getMessage());

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to edit person", ButtonType.OK);
                alert.show();
                log.error("Person Editing Error: " + e.getMessage());
            }
        });

        removeBtn.setOnAction(event -> {
            try {
                int id = Integer.parseInt(idTxt.getText());
                PersonService.delete(id);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Person Removed Successfully", ButtonType.OK);
                alert.show();
                resetForm();
                log.info("Person Deleted Successfully: " + id);

            } catch (PersonNotFoundException pnfe) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Person not found", ButtonType.OK);
                alert.show();
                log.warn("Person not found for deleting: " + pnfe.getMessage());

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to delete person", ButtonType.OK);
                alert.show();
                log.error("Person Deleting Error: " + e.getMessage());
            }
        });

        clearBtn.setOnAction(event -> resetForm());

        nameSearchTxt.setOnKeyReleased(event -> searchPersons());
        familySearchTxt.setOnKeyReleased(event -> searchPersons());

        EventHandler<Event> tableChangeEvent = event -> {
            Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                idTxt.setText(String.valueOf(selectedPerson.getId()));
                nameTxt.setText(selectedPerson.getName());
                familyTxt.setText(selectedPerson.getFamily());
                birthDate.setValue(selectedPerson.getBirthDate());
                phoneNumberTxt.setText(selectedPerson.getPhoneNumber());
                passwordPas.setText(selectedPerson.getPassword());
                userNameTxt.setText(selectedPerson.getUsername());
            }
        };

        personTable.setOnMouseReleased(tableChangeEvent);
        personTable.setOnKeyReleased(tableChangeEvent);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        familyCol.setCellValueFactory(new PropertyValueFactory<>("family"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        try {
            personList = PersonService.findAll();
            showPersonOnTable(personList);
        } catch (Exception e) {
            log.error("Error loading person list: " + e.getMessage());
        }
    }

    public void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        familyTxt.clear();
        phoneNumberTxt.clear();
        passwordPas.clear();
        userNameTxt.clear();
        nameSearchTxt.clear();
        familySearchTxt.clear();
        birthDate.setValue(LocalDate.now());

        try {
            personList = PersonService.findAll();
            showPersonOnTable(personList);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void showPersonOnTable(List<Person> personList) {
        ObservableList<Person> observableList = FXCollections.observableArrayList(personList);
        personTable.setItems(observableList);
    }

    private void searchPersons() {
        try {
            String name = nameSearchTxt.getText().trim();
            String family = familySearchTxt.getText().trim();
            List<Person> results = PersonService.findByNameAndFamily(name, family);
            showPersonOnTable(results);
        } catch (Exception e) {
            log.error("Search error: " + e.getMessage());
        }
    }
}
