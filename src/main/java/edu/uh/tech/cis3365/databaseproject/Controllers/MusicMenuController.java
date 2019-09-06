package edu.uh.tech.cis3365.databaseproject.Controllers;

import com.jfoenix.controls.JFXTextField;
import edu.uh.tech.cis3365.databaseproject.DatabaseProjectApplication;
import edu.uh.tech.cis3365.databaseproject.Models.Music;
import edu.uh.tech.cis3365.databaseproject.Repository.MusicRepository;
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
public class MusicMenuController implements Initializable {
    private Scene scene;
    private Stage window;
    private Music music;
    @FXML private TableView<Music> musicTable;
    @FXML private TableColumn<Music, Integer> songIdCol;
    @FXML private TableColumn<Music, String> titleCol, artistCol, albumCol, genreCol;
    @FXML private JFXTextField titleTxt, artistTxt, albumTxt, genreTxt;
    @FXML private TextField searchFieldTxt;
    @FXML private Button deleteBtn, updateBtn;
    private ObservableList<Music> musicData = FXCollections.observableArrayList();
    private PreparedStatement preparedStmt;
    private ResultSet rs;
    private Connection conn = DatabaseProjectApplication.conn;
    @Autowired
    private MusicRepository musicRepository;

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
        musicData.clear();
        try {
            preparedStmt = conn.prepareStatement("SELECT * FROM music");
            rs = preparedStmt.executeQuery();

            while (rs.next()) {
                musicData.add(new Music(rs.getInt("song_id"), rs.getString("song_name"), rs.getString("artist_name"), rs.getString("album_name"), rs.getString("genre_type")));
            }
            preparedStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void addMusic(ActionEvent actionEvent) {
        try {
            preparedStmt = conn.prepareStatement("INSERT INTO music(song_name, artist_name, album_name, genre_type) VALUES (?,?,?,?)");
            preparedStmt.setString(1, titleTxt.getText());
            preparedStmt.setString(2, artistTxt.getText());
            preparedStmt.setString(3, albumTxt.getText());
            preparedStmt.setString(4, genreTxt.getText());

            int i = preparedStmt.executeUpdate();
            if (i == 1) {
                System.out.println("Successfully added music.");
            }
            preparedStmt.close();
            clearFields();

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        refreshTable();
    }

    public void updateMusic(ActionEvent actionEvent) {
        music = musicTable.getSelectionModel().getSelectedItem();
        if (music != null) {
            updateBtn.setOnAction(e -> {
                try {
                    preparedStmt = conn.prepareStatement("UPDATE music SET song_name=?, artist_name=?, album_name=?, genre_type=? WHERE song_id='" + music.getSongId() + "'");
                    preparedStmt.setString(1, titleTxt.getText());
                    preparedStmt.setString(2, artistTxt.getText());
                    preparedStmt.setString(3, albumTxt.getText());
                    preparedStmt.setString(4, genreTxt.getText());

                    int i = preparedStmt.executeUpdate();
                    if (i == 1) {
                        System.out.println("Successfully updated song.");
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

    public void deleteMusic(ActionEvent actionEvent) {
        music = musicTable.getSelectionModel().getSelectedItem();
        if (music!=null) {
            deleteBtn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Are you sure you want to delete song?");
                alert.setHeaderText(null);
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {
                    try {
                        preparedStmt = conn.prepareStatement("DELETE FROM music WHERE song_id=?");
                        preparedStmt.setInt(1, music.getSongId());
                        int i = preparedStmt.executeUpdate();
                        if (i == 1) {
                            System.out.println("Successfully deleted song.");
                            refreshTable();
                        }
                        musicTable.getItems().remove(music);
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
        FilteredList<Music> filteredData = new FilteredList<>(musicData, e -> true);
        searchFieldTxt.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Music>) music -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(music.getSongId()).contains(newValue)) {
                    return true;
                } else if (music.getSongName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (music.getArtistName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (music.getAlbumName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (music.getGenreType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                clearFields();
                return false;
            });
        });

        SortedList<Music> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(musicTable.comparatorProperty());
        musicTable.setItems(sortedData);
    }

    public void setCellFactories() {
        songIdCol.setCellValueFactory(new PropertyValueFactory<>("songId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("songName"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        albumCol.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genreType"));
    }

    public void populateFields() {
        musicTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                music = musicTable.getItems().get(musicTable.getSelectionModel().getSelectedIndex());
                titleTxt.setText(music.getSongName());
                artistTxt.setText(music.getArtistName());
                albumTxt.setText(music.getAlbumName());
                genreTxt.setText(music.getGenreType());
            }
        });
    }

    public void clearFields() {
        titleTxt.clear();
        artistTxt.clear();
        albumTxt.clear();
        genreTxt.clear();

        /*musicTable.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                musicTable.getSelectionModel().clearSelection();
            }});*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        musicTable.setItems(null);
        setCellFactories();
        populateFields();
        refreshTable();
        searchFields();
    }

}
