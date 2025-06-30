package mft.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import mft.model.entity.AccountInfo;
import mft.model.entity.Person;
import mft.model.entity.TransactionType;
import mft.model.service.AccountInfoService;
import mft.model.service.PersonService;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class AccountViewController implements Initializable {

    @FXML
    private TextField idTxt, personIdTxt, amountTxt, timeTxt;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<TransactionType> transactionTypeCmb;

    @FXML
    private Button saveBtn, editBtn, removeBtn, clearBtn;

    @FXML
    private TableView<AccountInfo> accountTable;

    @FXML
    private TableColumn<AccountInfo, Integer> idCol;

    @FXML
    private TableColumn<AccountInfo, Integer> personCol;

    @FXML
    private TableColumn<AccountInfo, Integer> amountCol;

    @FXML
    private TableColumn<AccountInfo, String> typeCol;

    @FXML
    private TableColumn<AccountInfo, String> dateTimeCol;

    private ObservableList<AccountInfo> accountData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        transactionTypeCmb.setItems(FXCollections.observableArrayList(TransactionType.values()));
        clearForm();

        saveBtn.setOnAction(e -> save());
        editBtn.setOnAction(e -> edit());
        removeBtn.setOnAction(e -> delete());
        clearBtn.setOnAction(e -> clearForm());

        accountTable.setOnMouseClicked(e -> fillFormFromSelected());

        try {
            showAccountTable(AccountInfoService.findAll());
        } catch (Exception e) {
            log.error("Error loading account list: " + e.getMessage());
        }
    }

    private void clearForm() {
        idTxt.clear();
        personIdTxt.clear();
        amountTxt.clear();
        timeTxt.clear();
        transactionTypeCmb.getSelectionModel().clearSelection();
        datePicker.setValue(LocalDate.now());

        try {
            showAccountTable(AccountInfoService.findAll());
        } catch (Exception e) {
            log.error("Clear form error: " + e.getMessage());
        }
    }

    private void fillFormFromSelected() {
        AccountInfo selected = accountTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            idTxt.setText(String.valueOf(selected.getId()));
            personIdTxt.setText(String.valueOf(selected.getPerson().getId()));
            amountTxt.setText(String.valueOf(selected.getAmount()));
            transactionTypeCmb.setValue(selected.getTransactionType());
            datePicker.setValue(selected.getDateTime().toLocalDate());
            timeTxt.setText(selected.getDateTime().toLocalTime().toString());
        }
    }

    private void save() {
        try {
            Person person = PersonService.findById(Integer.parseInt(personIdTxt.getText()));
            LocalDateTime dateTime = LocalDateTime.of(datePicker.getValue(), LocalTime.parse(timeTxt.getText()));

            AccountInfo account = AccountInfo.builder()
                    .person(person)
                    .amount(Integer.parseInt(amountTxt.getText()))
                    .transactionType(transactionTypeCmb.getValue())
                    .dateTime(dateTime)
                    .build();

            AccountInfoService.save(account);
            clearForm();
            log.info("Account saved: " + account);
        } catch (Exception e) {
            log.error("Save error: " + e.getMessage());
        }
    }

    private void edit() {
        try {
            Person person = PersonService.findById(Integer.parseInt(personIdTxt.getText()));
            LocalDateTime dateTime = LocalDateTime.of(datePicker.getValue(), LocalTime.parse(timeTxt.getText()));

            AccountInfo account = AccountInfo.builder()
                    .id(Integer.parseInt(idTxt.getText()))
                    .person(person)
                    .amount(Integer.parseInt(amountTxt.getText()))
                    .transactionType(transactionTypeCmb.getValue())
                    .dateTime(dateTime)
                    .build();

            AccountInfoService.edit(account);
            clearForm();
            log.info("Account edited: " + account);
        } catch (Exception e) {
            log.error("Edit error: " + e.getMessage());
        }
    }

    private void delete() {
        try {
            int id = Integer.parseInt(idTxt.getText());
            AccountInfoService.delete(id);
            clearForm();
            log.info("Account deleted: ID " + id);
        } catch (Exception e) {
            log.error("Delete error: " + e.getMessage());
        }
    }

    private void showAccountTable(List<AccountInfo> list) {
        accountData.setAll(list);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        personCol.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getPerson().getId()));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        typeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                cell.getValue().getTransactionType().name()));
        dateTimeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                cell.getValue().getDateTime().toString()));

        accountTable.setItems(accountData);
    }
}
