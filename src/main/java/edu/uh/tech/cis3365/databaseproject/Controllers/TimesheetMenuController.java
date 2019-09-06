package edu.uh.tech.cis3365.databaseproject.Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import edu.uh.tech.cis3365.databaseproject.DatabaseProjectApplication;
import edu.uh.tech.cis3365.databaseproject.Models.Timesheet;
import edu.uh.tech.cis3365.databaseproject.Repository.TimesheetRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@Component
public class TimesheetMenuController implements Initializable {
    private Scene scene;
    private Stage window;
    private Timesheet timesheet;
    @FXML private TableView<Timesheet> timesheetTable;
    @FXML private TableColumn<Timesheet, Integer> timesheetIdCol, employeeIdCol;
    @FXML private TableColumn<Timesheet, Date> workDateCol;
    @FXML private TableColumn<Timesheet, Time> timeInCol, timeOutCol;
    @FXML private JFXComboBox employeeIdBox;
    @FXML private JFXDatePicker workDateTxt;
    @FXML private JFXTimePicker timeInTxt, timeOutTxt;
    @FXML private TextField searchFieldTxt;
    @FXML private Button deleteBtn, updateBtn;
    private ObservableList<Timesheet> timesheetData = FXCollections.observableArrayList();
    private PreparedStatement preparedStmt;
    private ResultSet rs;
    private Connection conn = DatabaseProjectApplication.conn;
    @Autowired
    private TimesheetRepository timesheetRepository;

    public void logout(ActionEvent actionEvent) throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("/Views/LoginMenu.fxml")));
        window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void back(ActionEvent actionEvent) throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml")));
        window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void refreshTable() {
        timesheetData.clear();
        try {
            preparedStmt = conn.prepareStatement("SELECT * FROM timesheet");
            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                timesheetData.add(new Timesheet(rs.getInt("timesheet_id"), rs.getInt("employee_id"), rs.getDate("work_day"), rs.getTime("time_in"), rs.getTime("time_out")));
            }
            preparedStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void addTimesheet(ActionEvent actionEvent) {
        try {
            preparedStmt = conn.prepareStatement("INSERT INTO timesheet(employee_id, work_day, time_in, time_out) VALUES (?,?,?,?)");
            preparedStmt.setInt(1, (Integer) employeeIdBox.getSelectionModel().getSelectedItem());
            preparedStmt.setDate(2, Date.valueOf(workDateTxt.getValue()));
            preparedStmt.setTime(3, Time.valueOf(timeInTxt.getValue()));
            preparedStmt.setTime(4, Time.valueOf(timeOutTxt.getValue()));

            int i = preparedStmt.executeUpdate();
            if (i == 1) {
                System.out.println("Successfully added timesheet.");
            }
            preparedStmt.close();
            clearFields();

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        refreshTable();
    }

    public void updateTimesheet(ActionEvent actionEvent) {
        timesheet = timesheetTable.getSelectionModel().getSelectedItem();
        if (timesheet!=null) {
        updateBtn.setOnAction(e -> {
            try {
                preparedStmt = conn.prepareStatement("UPDATE timesheet SET employee_id=?, work_day=?, time_in=?, time_out=? WHERE timesheet_id='" + timesheet.getTimesheetId() + "'");
                preparedStmt.setInt(1, (Integer) employeeIdBox.getSelectionModel().getSelectedItem());
                preparedStmt.setDate(2, Date.valueOf(workDateTxt.getValue()));
                preparedStmt.setTime(3, Time.valueOf(timeInTxt.getValue()));
                preparedStmt.setTime(4, Time.valueOf(timeOutTxt.getValue()));

                int i = preparedStmt.executeUpdate();
                if (i == 1) {
                    System.out.println("Successfully updated timesheet.");
                    refreshTable();
                }
                preparedStmt.close();
            } catch (SQLException ex) {
                System.out.println("Error: " + ex);
            }
        });
        }
    clearFields();
    }

    public void deleteTimesheet(ActionEvent actionEvent) {
        timesheet = timesheetTable.getSelectionModel().getSelectedItem();
        if (timesheet!=null) {
            deleteBtn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Are you sure you want to delete Timesheet?");
                alert.setHeaderText(null);
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {
                    try {
                        preparedStmt = conn.prepareStatement("DELETE FROM timesheet WHERE timesheet_id=?");
                        preparedStmt.setInt(1, timesheet.getTimesheetId());
                        int i = preparedStmt.executeUpdate();
                        if (i == 1) {
                            System.out.println("Successfully deleted song.");
                            refreshTable();
                        }
                        timesheetTable.getItems().remove(timesheet);
                        preparedStmt.close();
                    } catch (SQLException ex) {
                        System.out.println("Error: " + ex);
                    }
                }
            });
        }
        clearFields();
    }

    public void setChoiceBox(){
        try {
            preparedStmt = conn.prepareStatement("SELECT employee_id FROM employee");
            rs = preparedStmt.executeQuery();
            while (rs.next()) {
                employeeIdBox.getItems().addAll(rs.getInt(1));
            }
            preparedStmt.close();
            rs.close();

            employeeIdBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    employeeIdBox.getItems().get((Integer) newValue);
                }
            });
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void searchFields() {
        FilteredList<Timesheet> filteredData = new FilteredList<>(timesheetData, e -> true);
        searchFieldTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Timesheet>) timesheet -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(timesheet.getTimesheetId()).contains(newValue)) {
                    return true;
                } else if (String.valueOf(timesheet.getEmployeeId()).contains(newValue)) {
                        return true;
                    } else if (timesheet.getWorkDay().toLocalDate().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (timesheet.getTimeIn().toLocalTime().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (timesheet.getTimeOut().toLocalTime().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                clearFields();
                return false;
            });
        });

        SortedList<Timesheet> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(timesheetTable.comparatorProperty());
        timesheetTable.setItems(sortedData);
    }

    public void setCellFactories() {
        timesheetIdCol.setCellValueFactory(new PropertyValueFactory<>("timesheetId"));
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        workDateCol.setCellValueFactory(new PropertyValueFactory<>("workDay"));
        timeInCol.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        timeOutCol.setCellValueFactory(new PropertyValueFactory<>("timeOut"));
    }

    public void populateFields() {
        timesheetTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timesheet = timesheetTable.getItems().get(timesheetTable.getSelectionModel().getSelectedIndex());
                employeeIdBox.setValue(timesheet.getEmployeeId());
                workDateTxt.setValue(timesheet.getWorkDay().toLocalDate());
                timeInTxt.setValue(timesheet.getTimeIn().toLocalTime());
                timeOutTxt.setValue(timesheet.getTimeOut().toLocalTime());
            }
        });
    }

    public void clearFields() {
        employeeIdBox.getSelectionModel().clearSelection();
        workDateTxt.setValue(null);
        timeInTxt.setValue(null);
        timeOutTxt.setValue(null);

        /*timesheetTable.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                timesheetTable.getSelectionModel().clearSelection();
            }});*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timesheetTable.setItems(null);
        setChoiceBox();
        setCellFactories();
        populateFields();
        refreshTable();
        searchFields();
    }

}
