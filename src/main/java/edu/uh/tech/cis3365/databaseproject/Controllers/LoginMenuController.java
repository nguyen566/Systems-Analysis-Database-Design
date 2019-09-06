package edu.uh.tech.cis3365.databaseproject.Controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.uh.tech.cis3365.databaseproject.DatabaseProjectApplication;
import edu.uh.tech.cis3365.databaseproject.Models.UserInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class LoginMenuController {

    @FXML private JFXTextField usernameTxt;
    @FXML private JFXPasswordField passwordTxt;
    private Scene scene;
    private Stage window;
    private UserInfo user;
    private PreparedStatement preparedStmt = null;
    private ResultSet rs = null;
    private Connection conn = DatabaseProjectApplication.conn;

    public void login(ActionEvent event) throws Exception {
        if ((usernameTxt.getText().equals("admin") || usernameTxt.getText().equals("owner")) && passwordTxt.getText().equals("password")) {
            scene = new Scene(FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml")));
            window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else if (usernameTxt.getText().equals("") && passwordTxt.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Missing credentials");
            alert.setContentText("Please enter in your username and password.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid credentials");
            alert.setContentText("Please enter a valid username and password.");
            alert.showAndWait();
            usernameTxt.clear();
            passwordTxt.clear();
        }
      /*try {
            preparedStmt = conn.prepareStatement("SELECT * FROM user_info WHERE user_login=?");
            preparedStmt.setString(1, usernameTxt.getText());
            //preparedStmt.setString(2, passwordTxt.getText());
            rs = preparedStmt.executeQuery();

            if(rs.next()) {
                String dbhash = rs.getString("user_password");
                System.out.println(dbhash + "<br>");
                }
                /*if (BCrypt.checkpw(passwordTxt.getText(), dbhash)) {
                    System.out.println("It matches");
                } else {
                    System.out.println("It does not match");
                }//TODO: Authenticate user login by comparing user input and information from user table.
                preparedStmt.close();
                rs.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }*/
    }

    public void register(ActionEvent event) throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("/Views/UserRegistration.fxml")));
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void exit(ActionEvent event) {
        Platform.exit();
    }
}


