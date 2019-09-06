package edu.uh.tech.cis3365.databaseproject.Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.uh.tech.cis3365.databaseproject.Models.Client;
import edu.uh.tech.cis3365.databaseproject.DatabaseProjectApplication;
import edu.uh.tech.cis3365.databaseproject.Repository.ClientRepository;
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
public class ClientMenuController implements Initializable {
    private Scene scene;
    private Stage window;
    private Client client;
    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, Integer> clientIdCol;
    @FXML private TableColumn<Client, String> firstNameCol, lastNameCol, emailCol, phoneNumCol, streetCol, cityCol, stateCol, commentsCol, locationIdCol;
    @FXML private JFXTextField firstNameTxt, lastNameTxt, emailTxt, phoneNumTxt, streetTxt, cityTxt;
    @FXML private JFXComboBox stateBox;
    @FXML private JFXTextArea commentsTxt;
    @FXML private TextField searchFieldTxt;
    @FXML private Button deleteBtn, updateBtn;
    private ObservableList<Client> clientData = FXCollections.observableArrayList();
    private ObservableList<String> stateList = FXCollections.observableArrayList("","AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY");
    private PreparedStatement preparedStmt;
    private ResultSet rs;
    private Connection conn = DatabaseProjectApplication.conn;
    @Autowired
    private ClientRepository clientRepository;

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
        clientData.clear();
        try {
            preparedStmt = conn.prepareStatement("SELECT * FROM client");
            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                clientData.add(new Client(rs.getInt("Client_ID"), rs.getString("Client_First"), rs.getString("Client_Last"), rs.getString("Client_Email"), rs.getString("Client_Phone"), rs.getString("Client_Street"), rs.getString("Client_City"), rs.getString("Client_State"), rs.getString("Client_Comments"), rs.getInt("Location_ID")));
            }
            preparedStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void addClient(ActionEvent actionEvent) {
        try {
            preparedStmt = conn.prepareStatement("INSERT INTO client(client_first, client_last, client_email, client_phone, client_street, client_city, client_state, client_comments) VALUES (?,?,?,?,?,?,?,?)");
            preparedStmt.setString(1, firstNameTxt.getText());
            preparedStmt.setString(2, lastNameTxt.getText());
            preparedStmt.setString(3, emailTxt.getText());
            preparedStmt.setString(4, phoneNumTxt.getText());
            preparedStmt.setString(5, streetTxt.getText());
            preparedStmt.setString(6, cityTxt.getText());
            preparedStmt.setString(7, stateBox.getValue().toString());
            preparedStmt.setString(8, commentsTxt.getText());


            int i = preparedStmt.executeUpdate();
            if (i == 1) {
                System.out.println("Successfully added client.");
                refreshTable();
            }
            preparedStmt.close();
            clearFields();

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void updateClient(ActionEvent actionEvent) {
        client = clientTable.getSelectionModel().getSelectedItem();
        if (client!=null) {
            updateBtn.setOnAction(e -> {
                try {
                    preparedStmt = conn.prepareStatement("UPDATE client SET client_first=?, client_last=?, client_email=?, client_phone=?, client_street=?, client_city=?, client_state=?, client_comments=? WHERE client_id='" + client.getClientId() + "'");
                    preparedStmt.setString(1, firstNameTxt.getText());
                    preparedStmt.setString(2, lastNameTxt.getText());
                    preparedStmt.setString(3, emailTxt.getText());
                    preparedStmt.setString(4, phoneNumTxt.getText());
                    preparedStmt.setString(5, streetTxt.getText());
                    preparedStmt.setString(6, cityTxt.getText());
                    preparedStmt.setString(7, stateBox.getValue().toString());
                    preparedStmt.setString(8, commentsTxt.getText());

                    int i = preparedStmt.executeUpdate();
                    if (i == 1) {
                        System.out.println("Successfully updated client.");
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

    public void deleteClient(ActionEvent actionEvent) {
        client = clientTable.getSelectionModel().getSelectedItem();
        if (client!=null) {
            deleteBtn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Are you sure you want to delete Client?");
                alert.setHeaderText(null);
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {
                    try {
                        preparedStmt = conn.prepareStatement("DELETE FROM client WHERE client_id=?");
                        preparedStmt.setInt(1, client.getClientId());
                        int i = preparedStmt.executeUpdate();
                        if (i == 1) {
                            System.out.println("Successfully deleted client.");
                            refreshTable();
                        }
                        clientTable.getItems().remove(client);
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
        FilteredList<Client> filteredData = new FilteredList<>(clientData, e -> true);
        searchFieldTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Client>) client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(client.getClientId()).contains(newValue)) {
                    return true;
                } else if (client.getClientFirst().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (client.getClientLast().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (client.getClientEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (client.getClientPhone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (client.getClientStreet().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (client.getClientCity().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (client.getClientState().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (client.getClientComments().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                clearFields();
                return false;
            });
        });

        SortedList<Client> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(clientTable.comparatorProperty());
        clientTable.setItems(sortedData);
    }

    public void setCellFactories() {
        clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("clientFirst"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("clientLast"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("clientEmail"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));
        streetCol.setCellValueFactory(new PropertyValueFactory<>("clientStreet"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("clientCity"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("clientState"));
        commentsCol.setCellValueFactory(new PropertyValueFactory<>("clientComments"));
        locationIdCol.setCellValueFactory(new PropertyValueFactory<>("locationId"));
    }

    public void populateFields() {
        clientTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                client = clientTable.getItems().get(clientTable.getSelectionModel().getSelectedIndex());
                firstNameTxt.setText(client.getClientFirst());
                lastNameTxt.setText(client.getClientLast());
                emailTxt.setText(client.getClientEmail());
                phoneNumTxt.setText(client.getClientPhone());
                streetTxt.setText(client.getClientStreet());
                cityTxt.setText(client.getClientCity());
                stateBox.setValue(client.getClientState());
                commentsTxt.setText(client.getClientComments());
            }
        });
    }

    public void setChoiceBox() {
        stateBox.setItems(stateList);
    }

    public void clearFields() {
        firstNameTxt.clear();
        lastNameTxt.clear();
        emailTxt.clear();
        phoneNumTxt.clear();
        streetTxt.clear();
        cityTxt.clear();
        stateBox.getSelectionModel().clearSelection();
        commentsTxt.clear();

        /*clientTable.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                clientTable.getSelectionModel().clearSelection();
            }});*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientTable.setItems(null);
        setCellFactories();
        populateFields();
        setChoiceBox();
        refreshTable();
        searchFields();

    }

}
