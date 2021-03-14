

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.LinkedHashSet;


public class Playlist {

    private  static Stage playlistWindow;
    private static TableView playlistTableView;

    /*
    * Display user playlist
    * */
    public  static void displaySong(LinkedHashSet<Song> playlistDS)  {

        playlistWindow = new Stage();
        playlistWindow.initModality(Modality.APPLICATION_MODAL);
        playlistWindow.setTitle("Karaoke - Playlist");
        playlistWindow.setResizable(false);

        BorderPane playlistPane = new BorderPane();
        

       try {

            ///Initialise tableView columns
            TableColumn<Song,String> TitleColumn = new TableColumn<>("Title");
            TitleColumn.setCellValueFactory(new PropertyValueFactory<>("songTitle"));
            TitleColumn.setResizable(false);
            TitleColumn.setPrefWidth(595);
            TitleColumn.setSortable(false);
            
            playlistTableView = new TableView<>();
            playlistTableView.prefHeightProperty().bind(playlistPane.heightProperty());
            playlistTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

           //Display playlist song to tableview
           displayPlaylistSong(playlistDS);

            // Link columns to table
           playlistTableView.getColumns().add(TitleColumn);


        } catch (Exception e) {
           MessageBox.displayMessage("Error in loading playlist...");
           e.printStackTrace();
        }


        playlistPane.setCenter(playlistTableView);

        Button deleteBtn = new Button();
        deleteBtn.setText("REMOVE FROM PLAYLIST");
        deleteBtn.setTooltip(new Tooltip("Select a song to be deleted"));
        deleteBtn.setMinSize(170,50);
        deleteBtn.setOnAction(e->{

        //Retrieves highlighted index & converts into song
        int selectedRowIndex = playlistTableView.getSelectionModel().getSelectedIndex();
        if (selectedRowIndex != -1) {
            Song selectedSong = (Song) playlistTableView.getSelectionModel().getSelectedItem();
             deletePlaylistSong(selectedSong,playlistDS);
            refreshPlaylistTable(playlistDS);
        }else{
           if(playlistDS.isEmpty()){
               MessageBox.displayMessage("Playlist is already empty!");
               return;
           }
            MessageBox.displayMessage("Please select a song!");
        }


    });

        HBox deleteBox = new HBox();
        deleteBox.getChildren().addAll(deleteBtn);
        deleteBox.setAlignment(Pos.CENTER);
        deleteBox.setPadding(new Insets(7,7,7,7));
        playlistPane.setBottom(deleteBox);
        playlistPane.setStyle("-fx-background-color: #000000;");

        Scene playlistScene = new Scene(playlistPane,600,600);
        playlistScene.getStylesheets().add("file:stylesheet/Common.css");

        playlistWindow.setScene(playlistScene);
        playlistWindow.showAndWait();
        
    }

    /*
    * Set and display playlist songs to tableview
    *
    * @param LinkedHashSet playlistDS
    * */
    private static void displayPlaylistSong(LinkedHashSet<Song> playlistDS){

        ObservableList<Song> playlistSong = FXCollections.observableArrayList();

        //Iteration enhanced for loop
            for(Song s :  playlistDS){
                playlistSong.add(s);
            }

        playlistTableView.setItems(playlistSong);
            
    }

    /*
    * Refresh table content
    * @param LinkedHashSet playlistDs
    * */
    private static void refreshPlaylistTable(LinkedHashSet<Song> playlistDS){

        //Clear tableView items
        playlistTableView.getItems().clear();

        displayPlaylistSong(playlistDS);

    }

    /*
    * Delete a playlist song
    *
    * @params selectedSong & playlist linkedHashSet
    *
    * */
    public static void deletePlaylistSong(Song selectedSong, LinkedHashSet<Song> playlistDS){
        playlistDS.remove(selectedSong);
    }

}



