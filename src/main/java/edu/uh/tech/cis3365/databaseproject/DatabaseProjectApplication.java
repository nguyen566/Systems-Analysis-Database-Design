package edu.uh.tech.cis3365.databaseproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.Connection;

@SpringBootApplication
public class DatabaseProjectApplication extends Application {

    @Autowired
    private ConfigurableApplicationContext springContext;
    private Parent root;
    public static Connection conn;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(DatabaseProjectApplication.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/LoginMenu.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        root = fxmlLoader.load();
        conn = Driver.startConnection();
        super.init();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Lady BG Productions Management System");
        primaryStage.show();
    }
}
