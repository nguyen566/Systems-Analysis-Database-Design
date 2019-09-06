package edu.uh.tech.cis3365.databaseproject.Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import edu.uh.tech.cis3365.databaseproject.DatabaseProjectApplication;
import edu.uh.tech.cis3365.databaseproject.Models.Employee;
import edu.uh.tech.cis3365.databaseproject.Repository.EmployeeRepository;
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
public class EmployeeMenuController implements Initializable {
    private Scene scene;
    private Stage window;
    private Employee employee;
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, Integer> employeeIdCol;
    @FXML private TableColumn<Employee, String> firstNameCol, middleNameCol, lastNameCol, streetCol, cityCol, stateCol, phoneNumCol, positionCol, locationIdCol;
    @FXML private TableColumn<Employee, Date> dateOfBirthCol;
    @FXML private JFXTextField firstNameTxt, middleNameTxt, lastNameTxt, streetTxt, cityTxt, phoneNumTxt, positionTxt;
    @FXML private JFXDatePicker dateOfBirthTxt;
    @FXML private JFXComboBox stateBox;
    @FXML private TextField searchFieldTxt;
    @FXML private Button deleteBtn, updateBtn;
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    private ObservableList<String> stateList = FXCollections.observableArrayList("","AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY");
    private PreparedStatement preparedStmt;
    private ResultSet rs;
    private Connection conn = DatabaseProjectApplication.conn;
    @Autowired
    private EmployeeRepository employeeRepository;

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
        employeeData.clear();
        try {
            preparedStmt = conn.prepareStatement("SELECT * FROM employee");
            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                employeeData.add(new Employee(rs.getInt("employee_id"), rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), rs.getDate("birthdate"), rs.getString("street_address"), rs.getString("city"), rs.getString("state"), rs.getString("phone"), rs.getString("position"), rs. getInt("location_id")));
            }
            preparedStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void addEmployee(ActionEvent actionEvent) {
        try {
            preparedStmt = conn.prepareStatement("INSERT INTO employee(first_name, last_name, middle_name, birthdate, street_address, city, state, phone, position) VALUES (?,?,?,?,?,?,?,?,?)");
            preparedStmt.setString(1, firstNameTxt.getText());
            preparedStmt.setString(2, middleNameTxt.getText());
            preparedStmt.setString(3, lastNameTxt.getText());
            preparedStmt.setDate(4, Date.valueOf(dateOfBirthTxt.getValue()));
            preparedStmt.setString(5, streetTxt.getText());
            preparedStmt.setString(6, cityTxt.getText());
            preparedStmt.setString(7, stateBox.getValue().toString());
            preparedStmt.setString(8, phoneNumTxt.getText());
            preparedStmt.setString(9, positionTxt.getText());

            int i = preparedStmt.executeUpdate();
            if (i == 1) {
                System.out.println("Successfully added employee.");
            }
            preparedStmt.close();
            clearFields();

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        refreshTable();
    }

    public void updateEmployee(ActionEvent actionEvent) {
        employee = employeeTable.getSelectionModel().getSelectedItem();
        if (employee!=null) {
            updateBtn.setOnAction(e -> {
                try {
                    preparedStmt = conn.prepareStatement("UPDATE employee SET first_name=?, middle_name=?, last_name=?, birthdate=?, street_address=?, city=?, state=?, phone=?, position=? WHERE employee_id='" + employee.getEmployeeId() + "'");
                    preparedStmt.setString(1, firstNameTxt.getText());
                    preparedStmt.setString(2, middleNameTxt.getText());
                    preparedStmt.setString(3, lastNameTxt.getText());
                    preparedStmt.setDate(4, Date.valueOf(dateOfBirthTxt.getValue()));
                    preparedStmt.setString(5, streetTxt.getText());
                    preparedStmt.setString(6, cityTxt.getText());
                    preparedStmt.setString(7, stateBox.getValue().toString());
                    preparedStmt.setString(8, phoneNumTxt.getText());
                    preparedStmt.setString(9, positionTxt.getText());

                    int i = preparedStmt.executeUpdate();
                    if (i == 1) {
                        System.out.println("Successfully updated employee.");
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

    public void deleteEmployee(ActionEvent actionEvent) {
        employee = employeeTable.getSelectionModel().getSelectedItem();
        if (employee!=null) {
            deleteBtn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Are you sure you want to delete Employee?");
                alert.setHeaderText(null);
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {
                    try {
                        preparedStmt = conn.prepareStatement("DELETE FROM employee WHERE employee_id=?");
                        preparedStmt.setInt(1, employee.getEmployeeId());
                        int i = preparedStmt.executeUpdate();
                        if (i == 1) {
                            System.out.println("Successfully deleted employee.");
                            refreshTable();
                        }
                        employeeTable.getItems().remove(employee);
                        preparedStmt.close();
                    } catch (SQLException ex) {
                        System.out.println("Error: " + ex);
                    }
                }
            });
        }
        clearFields();
    }

    public void searchFields() {
        FilteredList<Employee> filteredData = new FilteredList<>(employeeData, e -> true);
        searchFieldTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Employee>) employee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(employee.getEmployeeId()).contains(newValue)) {
                    return true;
                } else if (employee.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getMiddleName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getBirthdate().toLocalDate().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getStreetAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getCity().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getState().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }  else if (employee.getPhone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getPosition().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                clearFields();
                return false;
            });
        });

        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());
        employeeTable.setItems(sortedData);
    }

    public void setCellFactories() {
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameCol.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        streetCol.setCellValueFactory(new PropertyValueFactory<>("streetAddress"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        locationIdCol.setCellValueFactory(new PropertyValueFactory<>("locationId"));
    }

    public void populateFields() {
        employeeTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                employee = employeeTable.getItems().get(employeeTable.getSelectionModel().getSelectedIndex());
                firstNameTxt.setText(employee.getFirstName());
                middleNameTxt.setText(employee.getMiddleName());
                lastNameTxt.setText(employee.getLastName());
                dateOfBirthTxt.setValue(employee.getBirthdate().toLocalDate());
                streetTxt.setText(employee.getState());
                cityTxt.setText(employee.getCity());
                stateBox.setValue(employee.getState());
                phoneNumTxt.setText(employee.getPhone());
                positionTxt.setText(employee.getPosition());
            }
        });
    }

    public void setChoiceBox() {
        stateBox.setItems(stateList);
    }

    public void clearFields() {
        firstNameTxt.clear();
        middleNameTxt.clear();
        lastNameTxt.clear();
        dateOfBirthTxt.setValue(null);
        streetTxt.clear();
        cityTxt.clear();
        stateBox.getSelectionModel().clearSelection();
        phoneNumTxt.clear();
        positionTxt.clear();

        /*employeeTable.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                employeeTable.getSelectionModel().clearSelection();
            }});*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeeTable.setItems(null);
        setCellFactories();
        populateFields();
        setChoiceBox();
        refreshTable();
        searchFields();

    }

}
