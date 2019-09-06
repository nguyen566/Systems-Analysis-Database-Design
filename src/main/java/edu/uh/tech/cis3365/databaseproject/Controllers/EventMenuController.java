package edu.uh.tech.cis3365.databaseproject.Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import edu.uh.tech.cis3365.databaseproject.DatabaseProjectApplication;
import edu.uh.tech.cis3365.databaseproject.Models.Event;
import edu.uh.tech.cis3365.databaseproject.Repository.EventRepository;
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
public class EventMenuController implements Initializable {
    private Scene scene;
    private Stage window;
    private Event event;
    @FXML private TableView<Event> eventTable;
    @FXML private TableColumn<Event, Integer> eventIdCol, clientIdCol;
    @FXML private TableColumn<Event, String> serviceRequestedCol, eventLocationCol;
    @FXML private TableColumn<Event, Date> eventDateCol;
    @FXML private TableColumn<Event, Time> startTimeCol, endTimeCol;
    @FXML private JFXTextField serviceRequestedTxt, eventLocationTxt;
    @FXML private JFXComboBox clientIdBox = new JFXComboBox<Integer>();;
    @FXML private JFXDatePicker eventDateTxt;
    @FXML private JFXTimePicker startTimeTxt, endTimeTxt;
    @FXML private TextField searchFieldTxt;
    @FXML private Button deleteBtn, updateBtn;
    private ObservableList<Event> eventData = FXCollections.observableArrayList();
    private PreparedStatement preparedStmt;
    private ResultSet rs;
    private Connection conn = DatabaseProjectApplication.conn;
    @Autowired
    private EventRepository eventRepository;

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
        eventData.clear();
        try {
            preparedStmt = conn.prepareStatement("SELECT * FROM event");
            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                eventData.add(new Event(rs.getInt("event_id"), rs.getString("service_requested"), rs.getDate("event_date"), rs.getInt("client_id"), rs.getTime("start_time"), rs.getTime("end_time"), rs.getString("event_location")));
            }
            preparedStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void addEvent(ActionEvent actionEvent) {
        try {
            preparedStmt = conn.prepareStatement("INSERT INTO event(service_requested, event_date, client_id, start_time, end_time, event_location) VALUES (?,?,?,?,?,?)");
            preparedStmt.setString(1, serviceRequestedTxt.getText());
            preparedStmt.setDate(2, Date.valueOf(eventDateTxt.getValue()));
            preparedStmt.setInt(3, (Integer) clientIdBox.getSelectionModel().getSelectedItem());
            preparedStmt.setTime(4, Time.valueOf(startTimeTxt.getValue()));
            preparedStmt.setTime(5, Time.valueOf(endTimeTxt.getValue()));
            preparedStmt.setString(6, eventLocationTxt.getText());

            int i = preparedStmt.executeUpdate();
            if (i == 1) {
                System.out.println("Successfully added event.");
            }
            preparedStmt.close();
            clearFields();

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        refreshTable();
    }

    public void updateEvent(ActionEvent actionEvent) {
        event = eventTable.getSelectionModel().getSelectedItem();
        if (event != null) {
            updateBtn.setOnAction(e -> {
                try {
                    preparedStmt = conn.prepareStatement("UPDATE event SET service_requested=?, event_date=?, client_id=?, start_time=?, end_time=?, event_location=? WHERE event_id='" + event.getEventId() + "'");
                    preparedStmt.setString(1, serviceRequestedTxt.getText());
                    preparedStmt.setDate(2, Date.valueOf(eventDateTxt.getValue()));
                    preparedStmt.setInt(3, (Integer) clientIdBox.getSelectionModel().getSelectedItem());
                    preparedStmt.setTime(4, Time.valueOf(startTimeTxt.getValue()));
                    preparedStmt.setTime(5, Time.valueOf(endTimeTxt.getValue()));
                    preparedStmt.setString(6, eventLocationTxt.getText());

                    int i = preparedStmt.executeUpdate();
                    if (i == 1) {
                        System.out.println("Successfully updated event.");
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

    public void deleteEvent(ActionEvent actionEvent) {
        event = eventTable.getSelectionModel().getSelectedItem();
        if (event!=null) {
            deleteBtn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Are you sure you want to delete Event?");
                alert.setHeaderText(null);
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {
                    try {
                        preparedStmt = conn.prepareStatement("DELETE FROM event WHERE event_id=?");
                        preparedStmt.setInt(1, event.getEventId());
                        int i = preparedStmt.executeUpdate();
                        if (i == 1) {
                            System.out.println("Successfully deleted event.");
                            refreshTable();
                        }
                        eventTable.getItems().remove(event);
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
            preparedStmt = conn.prepareStatement("SELECT client_id FROM client");
            rs = preparedStmt.executeQuery();
            while (rs.next()) {
                clientIdBox.getItems().addAll(rs.getInt(1));
            }
            preparedStmt.close();
            rs.close();

            clientIdBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    clientIdBox.getItems().get((Integer) newValue);
                }
            });
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void searchFields() {
        FilteredList<Event> filteredData = new FilteredList<>(eventData, e -> true);
        searchFieldTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Event>) event -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(event.getEventId()).contains(newValue)) {
                    return true;
                } else if (event.getServiceRequested().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (event.getEventDate().toLocalDate().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(event.getEventId()).contains(newValue)) {
                    return true;
                } else if (event.getStartTime().toLocalTime().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (event.getEndTime().toLocalTime().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (event.getEventLocation().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                clearFields();
                return false;
            });
        });

        SortedList<Event> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(eventTable.comparatorProperty());
        eventTable.setItems(sortedData);
    }

    public void setCellFactories() {
        eventIdCol.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        serviceRequestedCol.setCellValueFactory(new PropertyValueFactory<>("serviceRequested"));
        eventDateCol.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        eventLocationCol.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));
    }

    public void populateFields() {
        eventTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                event = eventTable.getItems().get(eventTable.getSelectionModel().getSelectedIndex());
                serviceRequestedTxt.setText(event.getServiceRequested());
                eventDateTxt.setValue(event.getEventDate().toLocalDate());
                clientIdBox.setValue(event.getClientId());
                startTimeTxt.setValue(event.getStartTime().toLocalTime());
                endTimeTxt.setValue(event.getEndTime().toLocalTime());
                eventLocationTxt.setText(event.getEventLocation());
            }
        });
    }

    public void clearFields() {
        serviceRequestedTxt.clear();
        eventDateTxt.setValue(null);
        clientIdBox.getSelectionModel().clearSelection();
        startTimeTxt.setValue(null);
        endTimeTxt.setValue(null);
        eventLocationTxt.clear();

        /*eventTable.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                eventTable.getSelectionModel().clearSelection();
            }});*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventTable.setItems(null);
        setCellFactories();
        fillComboBox();
        populateFields();
        refreshTable();
        searchFields();
    }

}
