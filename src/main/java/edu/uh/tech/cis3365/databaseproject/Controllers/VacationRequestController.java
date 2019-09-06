package edu.uh.tech.cis3365.databaseproject.Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import edu.uh.tech.cis3365.databaseproject.DatabaseProjectApplication;
import edu.uh.tech.cis3365.databaseproject.Models.VacationRequest;
import edu.uh.tech.cis3365.databaseproject.Repository.VacationRequestRepository;
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
public class VacationRequestController implements Initializable {
    private Scene scene;
    private Stage window;
    private VacationRequest vacationRequest;
    @FXML private TableView<VacationRequest> vacationRequestTable;
    @FXML private TableColumn<VacationRequest, Integer> vacationIdCol, employeeIdCol;
    @FXML private TableColumn<VacationRequest, String> reasonCol;
    @FXML private TableColumn<VacationRequest, Date> dateRequestedCol, dateReturnCol;
    @FXML private JFXTextArea reasonTxt;
    @FXML private JFXComboBox employeeIdBox;
    @FXML private JFXDatePicker dateRequestedTxt, dateReturnTxt;
    @FXML private TextField searchFieldTxt;
    @FXML private Button deleteBtn, updateBtn;
    private ObservableList<VacationRequest> vacationRequestData = FXCollections.observableArrayList();
    private PreparedStatement preparedStmt;
    private ResultSet rs;
    private Connection conn = DatabaseProjectApplication.conn;
    @Autowired
    private VacationRequestRepository vacationRequestRepository;

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
        vacationRequestData.clear();
        try {
            preparedStmt = conn.prepareStatement("SELECT * FROM vacation_request");
            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                vacationRequestData.add(new VacationRequest(rs.getInt("vacation_id"), rs.getDate("date_requested"), rs.getDate("date_return"), rs.getInt("employee_id"), rs.getString("reason")));
            }
            preparedStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void addVacationRequest(ActionEvent actionEvent) {
        try {
            preparedStmt = conn.prepareStatement("INSERT INTO vacation_request(date_requested, date_return, employee_id, reason) VALUES (?,?,?,?)");
            preparedStmt.setDate(1, Date.valueOf(dateRequestedTxt.getValue()));
            preparedStmt.setDate(2, Date.valueOf(dateReturnTxt.getValue()));
            preparedStmt.setInt(3, (Integer) employeeIdBox.getSelectionModel().getSelectedItem());
            preparedStmt.setString(4, reasonTxt.getText());

            int i = preparedStmt.executeUpdate();
            if (i == 1) {
                System.out.println("Successfully added Vacation Request.");
            }
            preparedStmt.close();
            clearFields();

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        refreshTable();
    }

    public void updateVacationRequest(ActionEvent actionEvent) {
        vacationRequest = vacationRequestTable.getSelectionModel().getSelectedItem();
        if (vacationRequest!=null) {
            updateBtn.setOnAction(e -> {
                try {
                    preparedStmt = conn.prepareStatement("UPDATE vacation_request SET date_requested=?, date_return=?, employee_id=?, reason=?, WHERE vacation_id='" + vacationRequest.getVacationId() + "'");
                    preparedStmt.setDate(1, Date.valueOf(dateRequestedTxt.getValue()));
                    preparedStmt.setDate(2, Date.valueOf(dateReturnTxt.getValue()));
                    preparedStmt.setInt(3, (Integer) employeeIdBox.getSelectionModel().getSelectedItem());
                    preparedStmt.setString(4, reasonTxt.getText());


                    int i = preparedStmt.executeUpdate();
                    if (i == 1) {
                        System.out.println("Successfully updated Vacation Request.");
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

    public void deleteVacationRequest(ActionEvent actionEvent) {
        vacationRequest = vacationRequestTable.getSelectionModel().getSelectedItem();
        if (vacationRequest!=null) {
            deleteBtn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Are you sure you want to delete Vacation Request?");
                alert.setHeaderText(null);
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {
                    try {
                        preparedStmt = conn.prepareStatement("DELETE FROM vacation_request WHERE vacation_id=?");
                        preparedStmt.setInt(1, vacationRequest.getVacationId());
                        int i = preparedStmt.executeUpdate();
                        if (i == 1) {
                            System.out.println("Successfully deleted Vacation Request.");
                            refreshTable();
                        }
                        vacationRequestTable.getItems().remove(vacationRequest);
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
            preparedStmt = conn.prepareStatement("SELECT employee_id FROM vacation_request");
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
        FilteredList<VacationRequest> filteredData = new FilteredList<>(vacationRequestData, e -> true);
        searchFieldTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super VacationRequest>) vacationRequest -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(vacationRequest.getVacationId()).contains(newValue)) {
                    return true;
                } else if (String.valueOf(vacationRequest.getEmployeeId()).contains(newValue)) {
                    return true;
                }  else if (vacationRequest.getReason().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (vacationRequest.getDateRequested().toLocalDate().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }  else if (vacationRequest.getDateReturn().toLocalDate().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                clearFields();
                return false;
            });
        });

        SortedList<VacationRequest> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(vacationRequestTable.comparatorProperty());
        vacationRequestTable.setItems(sortedData);
    }

    public void setCellFactories() {
        vacationIdCol.setCellValueFactory(new PropertyValueFactory<>("vacationId"));
        dateRequestedCol.setCellValueFactory(new PropertyValueFactory<>("dateRequested"));
        dateReturnCol.setCellValueFactory(new PropertyValueFactory<>("dateReturn"));
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
    }

    public void populateFields() {
        vacationRequestTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                vacationRequest = vacationRequestTable.getItems().get(vacationRequestTable.getSelectionModel().getSelectedIndex());
                employeeIdBox.setValue(vacationRequest.getEmployeeId());
                reasonTxt.setText(vacationRequest.getReason());
                dateRequestedTxt.setValue(vacationRequest.getDateRequested().toLocalDate());
                dateReturnTxt.setValue(vacationRequest.getDateReturn().toLocalDate());
            }
        });
    }

    public void clearFields() {
        employeeIdBox.getSelectionModel().clearSelection();
        reasonTxt.clear();
        dateRequestedTxt.setValue(null);
        dateReturnTxt.setValue(null);

        /*vacationRequestTable.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                vacationRequestTable.getSelectionModel().clearSelection();
            }});*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vacationRequestTable.setItems(null);
        setChoiceBox();
        setCellFactories();
        populateFields();
        refreshTable();
        searchFields();
    }

}
