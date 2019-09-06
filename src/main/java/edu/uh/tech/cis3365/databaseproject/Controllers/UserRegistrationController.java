package edu.uh.tech.cis3365.databaseproject.Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.uh.tech.cis3365.databaseproject.DatabaseProjectApplication;
import edu.uh.tech.cis3365.databaseproject.Repository.UserInfoRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserRegistrationController implements Initializable {

    private Scene scene;
    private Stage window;
    @FXML
    private JFXTextField firstNameTxt, lastNameTxt, usernameTxt;
    @FXML
    private JFXPasswordField passwordTxt;
    @FXML
    private JFXComboBox employeeIdBox, statusBox;
    private ObservableList<String> statusList = FXCollections.observableArrayList("", "ACTIVE", "INACTIVE", "LOA");
    private PreparedStatement preparedStmt;
    private ResultSet rs;
    private Connection conn = DatabaseProjectApplication.conn;
    @Autowired
    private UserInfoRepository userInfoRepository;

    public void back(ActionEvent actionEvent) throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("/Views/LoginMenu.fxml")));
        window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void registerUser(ActionEvent actionEvent) throws IOException {
        try {
            preparedStmt = conn.prepareStatement("INSERT INTO user_info(first_name, last_name, employee_id, user_login, user_password, user_status) VALUES (?,?,?,?,?,?)");
            preparedStmt.setString(1, firstNameTxt.getText());
            preparedStmt.setString(2, lastNameTxt.getText());
            preparedStmt.setInt(3, (Integer) employeeIdBox.getSelectionModel().getSelectedItem());
            preparedStmt.setString(4, usernameTxt.getText());
            preparedStmt.setString(5, BCrypt.hashpw(passwordTxt.getText(), BCrypt.gensalt()));
            preparedStmt.setString(6, statusBox.getSelectionModel().getSelectedItem().toString());

            int i = preparedStmt.executeUpdate();
            if (i == 1) {
                System.out.println("Successfully added user.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setContentText("Successfully added user.");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
            preparedStmt.close();
            clearFields();
            back(actionEvent);

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void setChoiceBox() {
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
        statusBox.setItems(statusList);
    }

    public void clearFields() {
        firstNameTxt.clear();
        lastNameTxt.clear();
        employeeIdBox.getSelectionModel().clearSelection();
        usernameTxt.clear();
        passwordTxt.clear();
        statusBox.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setChoiceBox();
    }

}
