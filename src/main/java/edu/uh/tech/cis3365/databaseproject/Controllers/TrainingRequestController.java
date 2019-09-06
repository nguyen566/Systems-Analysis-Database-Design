package edu.uh.tech.cis3365.databaseproject.Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import edu.uh.tech.cis3365.databaseproject.DatabaseProjectApplication;
import edu.uh.tech.cis3365.databaseproject.Models.EmployeeTrainingRequest;
import edu.uh.tech.cis3365.databaseproject.Repository.EmployeeTrainingRequestRepository;
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
public class TrainingRequestController implements Initializable {
    private Scene scene;
    private Stage window;
    private EmployeeTrainingRequest trainingRequest;
    @FXML private TableView<EmployeeTrainingRequest> trainingRequestTable;
    @FXML private TableColumn<EmployeeTrainingRequest, Integer> trainingRequestIdCol, employeeIdCol;
    @FXML private TableColumn<EmployeeTrainingRequest, String> descriptionCol, trainingTypeCol;
    @FXML private TableColumn<EmployeeTrainingRequest, Date> trainingDateCol;
    @FXML private JFXTextArea descriptionTxt;
    @FXML private JFXComboBox<Integer> employeeIdBox;
    @FXML private JFXComboBox<String> trainingTypeBox;
    @FXML private JFXDatePicker trainingDateTxt;
    @FXML private TextField searchFieldTxt;
    @FXML private Button deleteBtn, updateBtn;
    private ObservableList<EmployeeTrainingRequest> trainingRequestData = FXCollections.observableArrayList();
    private ObservableList<String> typeList = FXCollections.observableArrayList("", "orientation", "onboarding", "technical", "managerial");
    private PreparedStatement preparedStmt;
    private ResultSet rs;
    private Connection conn = DatabaseProjectApplication.conn;
    @Autowired
    private EmployeeTrainingRequestRepository trainingRequestRepository;

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
        trainingRequestData.clear();
        try {
            preparedStmt = conn.prepareStatement("SELECT * FROM employee_training_request");
            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                trainingRequestData.add(new EmployeeTrainingRequest(rs.getInt("Training_RequestID"), rs.getInt("Employee_ID"), rs.getDate("Training_Date"), rs.getString("Training_Description"), rs.getString("Training_Type")));
            }
            preparedStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void addTrainingRequest(ActionEvent actionEvent) {
        try {
            preparedStmt = conn.prepareStatement("INSERT INTO employee_training_request(employee_id, training_date, training_description, training_type) VALUES (?,?,?,?)");
            preparedStmt.setInt(1, employeeIdBox.getSelectionModel().getSelectedItem());
            preparedStmt.setDate(2, Date.valueOf(trainingDateTxt.getValue()));
            preparedStmt.setString(3, descriptionTxt.getText());
            preparedStmt.setString(4, trainingTypeBox.getSelectionModel().getSelectedItem());

            int i = preparedStmt.executeUpdate();
            if (i == 1) {
                System.out.println("Successfully added Training Request.");
            }
            preparedStmt.close();
            clearFields();

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        refreshTable();
    }

    public void updateTrainingRequest(ActionEvent actionEvent) {
        trainingRequest = trainingRequestTable.getSelectionModel().getSelectedItem();
        if (trainingRequest!=null) {
            updateBtn.setOnAction(e -> {
                try {
                    preparedStmt = conn.prepareStatement("UPDATE employee_training_request SET employee_id=?, training_date=?, training_description=?, training_type=?, WHERE training_requestid='" + trainingRequest.getTrainingRequestid() + "'");
                    preparedStmt.setInt(1, employeeIdBox.getSelectionModel().getSelectedItem());
                    preparedStmt.setDate(2, Date.valueOf(trainingDateTxt.getValue()));
                    preparedStmt.setString(3, descriptionTxt.getText());
                    preparedStmt.setString(4, trainingTypeBox.getSelectionModel().getSelectedItem());

                    int i = preparedStmt.executeUpdate();
                    if (i == 1) {
                        System.out.println("Successfully updated Training Request.");
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

    public void deleteTrainingRequest(ActionEvent actionEvent) {
        trainingRequest = trainingRequestTable.getSelectionModel().getSelectedItem();
        if (trainingRequest!=null) {
            deleteBtn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Are you sure you want to delete Training Request?");
                alert.setHeaderText(null);
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {
                    try {
                        preparedStmt = conn.prepareStatement("DELETE FROM employee_training_request WHERE training_requestid=?");
                        preparedStmt.setInt(1, trainingRequest.getTrainingRequestid());
                        int i = preparedStmt.executeUpdate();
                        if (i == 1) {
                            System.out.println("Successfully deleted Training Request.");
                            refreshTable();
                        }
                        trainingRequestTable.getItems().remove(trainingRequest);
                        preparedStmt.close();
                    } catch (SQLException ex) {
                        System.out.println("Error: " + ex);
                    }
                }
            });
        }
        clearFields();
    }

    public void fillComboBox(){
        try {
            preparedStmt = conn.prepareStatement("SELECT employee_id FROM employee_training_request");
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
        trainingTypeBox.setItems(typeList);

    }

    public void searchFields() {
        FilteredList<EmployeeTrainingRequest> filteredData = new FilteredList<>(trainingRequestData, e -> true);
        searchFieldTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super EmployeeTrainingRequest>) trainingRequest -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(trainingRequest.getTrainingRequestid()).contains(newValue)) {
                    return true;
                } else if (String.valueOf(trainingRequest.getEmployeeId()).contains(newValue)) {
                    return true;
                }  else if (trainingRequest.getTrainingDate().toLocalDate().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }  else if (trainingRequest.getTrainingDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (trainingRequest.getTrainingType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                clearFields();
                return false;
            });
        });

        SortedList<EmployeeTrainingRequest> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(trainingRequestTable.comparatorProperty());
        trainingRequestTable.setItems(sortedData);
    }

    public void setCellFactories() {
        trainingRequestIdCol.setCellValueFactory(new PropertyValueFactory<>("trainingRequestid"));
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        trainingDateCol.setCellValueFactory(new PropertyValueFactory<>("trainingDate"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("trainingDescription"));
        trainingTypeCol.setCellValueFactory(new PropertyValueFactory<>("trainingType"));
    }

    public void populateFields() {
        trainingRequestTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                trainingRequest = trainingRequestTable.getItems().get(trainingRequestTable.getSelectionModel().getSelectedIndex());
                employeeIdBox.setValue(trainingRequest.getEmployeeId());
                trainingDateTxt.setValue(trainingRequest.getTrainingDate().toLocalDate());
                descriptionTxt.setText(trainingRequest.getTrainingDescription());
                trainingTypeBox.setValue(trainingRequest.getTrainingType());
            }
        });
    }

    public void clearFields() {
        employeeIdBox.getSelectionModel().clearSelection();
        trainingDateTxt.setValue(null);
        descriptionTxt.clear();
        trainingTypeBox.setValue(null);

        /*trainingRequestTable.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                trainingRequestTable.getSelectionModel().clearSelection();
            }});*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trainingRequestTable.setItems(null);
        fillComboBox();
        setCellFactories();
        populateFields();
        refreshTable();
        searchFields();
    }

}
