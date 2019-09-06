package edu.uh.tech.cis3365.databaseproject.Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.uh.tech.cis3365.databaseproject.DatabaseProjectApplication;
import edu.uh.tech.cis3365.databaseproject.Models.UserInfo;
import edu.uh.tech.cis3365.databaseproject.Repository.UserInfoRepository;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@Component
public class UserMenuController implements Initializable {

    private Scene scene;
    private Stage window;
    private UserInfo user;
    @FXML private TableView<UserInfo> userTable;
    @FXML private TableColumn<UserInfo, Integer> userIdCol;
    @FXML private TableColumn<UserInfo, String> firstNameCol, lastNameCol, usernameCol, employeeIdCol, statusCol;
    @FXML private JFXTextField firstNameTxt, lastNameTxt, usernameTxt;
    @FXML private JFXComboBox employeeIdBox, statusBox;
    @FXML private TextField searchFieldTxt;
    @FXML private Button deleteBtn, updateBtn;
    private ObservableList<UserInfo> userData = FXCollections.observableArrayList();
    private ObservableList<String> statusList = FXCollections.observableArrayList("", "ACTIVE", "INACTIVE", "LOA");
    private PreparedStatement preparedStmt;
    private ResultSet rs;
    private Connection conn = DatabaseProjectApplication.conn;
    @Autowired
    private UserInfoRepository userInfoRepository;

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
        userData.clear();
        try {
            preparedStmt = conn.prepareStatement("SELECT * FROM user_info");
            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                userData.add(new UserInfo(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getInt("employee_id"), rs.getString("user_login"), rs.getString("user_status")));
            }
            preparedStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void registerUser(ActionEvent actionEvent) throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("/Views/UserRegistration.fxml")));
        window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void updateUser(ActionEvent actionEvent) {
        user = userTable.getSelectionModel().getSelectedItem();
        if (user != null) {
            updateBtn.setOnAction(e -> {
                try {
                    preparedStmt = conn.prepareStatement("UPDATE user_info SET first_name=?, last_name=?, employee_id=?, user_login=?, user_status=? WHERE user_id='" + user.getUserId() + "'");
                    preparedStmt.setString(1, firstNameTxt.getText());
                    preparedStmt.setString(2, lastNameTxt.getText());
                    preparedStmt.setInt(3, (Integer) employeeIdBox.getSelectionModel().getSelectedItem());
                    preparedStmt.setString(4, usernameTxt.getText());
                    preparedStmt.setString(5, statusBox.getValue().toString());

                    int i = preparedStmt.executeUpdate();
                    if (i == 1) {
                        System.out.println("Successfully updated user.");
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

    public void deleteUser(ActionEvent actionEvent) {
        user = userTable.getSelectionModel().getSelectedItem();
        if (user!=null) {
            deleteBtn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Are you sure you want to delete UserInfo?");
                alert.setHeaderText(null);
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {
                    try {
                        preparedStmt = conn.prepareStatement("DELETE FROM user_info WHERE user_id=?");
                        preparedStmt.setInt(1, user.getUserId());
                        int i = preparedStmt.executeUpdate();
                        if (i == 1) {
                            System.out.println("Successfully deleted user.");
                            refreshTable();
                        }
                        userTable.getItems().remove(user);
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
        FilteredList<UserInfo> filteredData = new FilteredList<>(userData, e -> true);
        searchFieldTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super UserInfo>) user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(user.getUserId()).contains(newValue)) {
                    return true;
                } else if (user.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(user.getEmployeeId()).contains(newValue)) {
                    return true;
                }  else if (user.getUserLogin().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getUserStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                clearFields();
                return false;
            });
        });

        SortedList<UserInfo> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(userTable.comparatorProperty());
        userTable.setItems(sortedData);
    }

    public void setCellFactories() {
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("userLogin"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("userStatus"));
    }

    public void populateFields() {
        userTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                user = userTable.getItems().get(userTable.getSelectionModel().getSelectedIndex());
                firstNameTxt.setText(user.getFirstName());
                lastNameTxt.setText(user.getLastName());
                employeeIdBox.setValue(user.getEmployeeId());
                usernameTxt.setText(user.getUserLogin());
                statusBox.setValue(user.getUserStatus());
            }
        });
    }

    public void setChoiceBox() {
        try {
            preparedStmt = conn.prepareStatement("SELECT employee_id FROM user_info");
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
        statusBox.setItems(statusList);
    }

    public void clearFields() {
        firstNameTxt.clear();
        lastNameTxt.clear();
        employeeIdBox.getSelectionModel().clearSelection();
        usernameTxt.clear();
        statusBox.getSelectionModel().clearSelection();

        /*userTable.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                userTable.getSelectionModel().clearSelection();
            }});*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userTable.setItems(null);
        setCellFactories();
        populateFields();
        setChoiceBox();
        refreshTable();
        searchFields();

    }

}
